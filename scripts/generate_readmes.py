import os
import sys

print("--- [1/3] Arrancando sistema y cargando librerias... ---", flush=True)

import argparse
import time
import google.generativeai as genai
from google.api_core import exceptions
from pathlib import Path

# --- CONFIGURATION ---
print("--- [2/3] Configurando conexion con Gemini AI... ---", flush=True)
API_KEY = os.environ.get("GEMINI_API_KEY")
genai.configure(api_key=API_KEY)

# Use Gemini Flash Latest for best compatibility and rate limits
MODEL_NAME = 'gemini-flash-latest'
model = genai.GenerativeModel(MODEL_NAME)

EXCLUDE_DIRS = {
    '.git', 'node_modules', '.github', 'scripts', 'scratch', '__pycache__', 
    'bin', 'obj', 'build', 'dist', 'target', '.idea', '.vs', '.gradle',
    'src', 'CSS', 'Fotos', 'JavaScript', 'Html', 'images', 'Assets', 'Recursos', 'js', 'css', 'html', 'fotos'
}

EXTENSIONS = {'.java', '.py', '.html', '.ino', '.cpp', '.c', '.js', '.ts', '.sh', '.bat'}

AI_SIGNATURE = "<!-- AI-GENERATED-README -->"

PROMPT_TEMPLATE = """
You are an expert technical writer specializing in creating premium README.md files for student portfolios.
Analyze the following directory structure and code samples from a project folder.
Generate a high-quality, professional, and BILINGUAL (English and Spanish) README.md.

### AESTHETIC GUIDELINES:
1. Use relevant emojis for titles.
2. Structure:
   - # Title (Bilingual)
   - ## 📋 Description | Descripción (A paragraph describing the project's purpose based on the code).
   - ## ✨ Key Features | Características Clave (Bullet points of what the code does).
   - ## 🛠️ Tech Stack | Tecnologías (List of languages and tools used).
   - ## 📂 Project Structure | Estructura del Proyecto (A simplified tree or list of files).
   - ## 🚀 How to Run | Cómo Ejecutar (Instructions derived from the file types).

### CRITICAL:
- Return ONLY the content of the README.md file.
- Do NOT include any introductory text, reasoning, or "Here is your README" messages.
- Do NOT wrap the result in markdown code blocks like ```markdown.

### CONTEXT:
Folder Name: {folder_name}
File List: {file_list}
Code Context (Main File):
```
{code_context}
```
"""

def get_project_roots(root_dir):
    project_roots = []
    print("--- [3/3] Escaneando carpetas (esto puede tardar)... ---", flush=True)
    for root, dirs, files in os.walk(root_dir):
        # Filter directories to avoid scanning junk
        dirs[:] = [d for d in dirs if d not in EXCLUDE_DIRS]
        
        relative_path = os.path.relpath(root, root_dir)
        if relative_path != ".":
            # Print every folder to show constant activity
            print(f"  Scanning: {relative_path[:60]}...", end="\r", flush=True)

        # Check if there are source files in THIS directory
        has_source = any(Path(f).suffix in EXTENSIONS for f in files)
        
        if has_source:
            # We found a potential project root
            project_roots.append(Path(root))
    
    print("\n[OK] Scan complete.")
    return project_roots

def generate_readme(folder_path, force=False):
    readme_path = folder_path / "README.md"
    
    # Smart Skip Logic
    if readme_path.exists() and not force:
        try:
            with open(readme_path, 'r', encoding='utf-8') as f:
                existing_content = f.read()
            if AI_SIGNATURE in existing_content:
                print(f"Skipping (Already AI-generated): {folder_path.name}")
                return
            else:
                print(f"Skipping (Manual README detected): {folder_path.name}")
                return
        except:
            pass

    print(f"Generating README for: {folder_path}")
    
    files = list(folder_path.iterdir())
    file_list = ", ".join([f.name for f in files])
    
    # Try to find the most "important" file for context
    source_files = [f for f in files if f.suffix in EXTENSIONS]
    if not source_files:
        return
    
    main_file = max(source_files, key=lambda f: f.stat().st_size)
    
    print(f"  [1/4] Reading source code context...", flush=True)
    try:
        with open(main_file, 'r', encoding='utf-8') as f:
            code_context = f.read(5000) # Slightly more context
    except Exception as e:
        code_context = f"[Error reading file: {e}]"

    print(f"  [2/4] Preparing prompt for {folder_path.name}...", flush=True)
    prompt = PROMPT_TEMPLATE.format(
        folder_name=folder_path.name,
        file_list=file_list,
        code_context=code_context
    )

    print(f"  [3/4] Calling Gemini AI API (Waiting for response)...", flush=True)
    try:
        # Retry logic for 429 errors
        retries = 5
        response = None
        for i in range(retries):
            try:
                response = model.generate_content(prompt)
                break
            except exceptions.ResourceExhausted:
                wait_time = (i + 1) * 30 # Longer wait for free tier
                print(f"  !! Rate limit hit. Cooling down for {wait_time}s... ({i+1}/{retries})", flush=True)
                time.sleep(wait_time)
        
        if not response:
            print(f"  ERROR: Failed after all retries for {folder_path.name}", flush=True)
            return

        print(f"  [4/4] Writing README.md...", flush=True)
        content = response.text.strip()
        
        # Clean up any potential markdown block markers from AI
        if content.startswith("```markdown"):
            content = content[len("```markdown"):].strip()
        if content.endswith("```"):
            content = content[:-3].strip()

        # Add AI Signature
        content = f"{content}\n\n{AI_SIGNATURE}"

        with open(readme_path, 'w', encoding='utf-8') as f:
            f.write(content)
        
        print(f"DONE: Created README.md in {folder_path.name}")
    except Exception as e:
        print(f"ERROR: Error generating README for {folder_path.name}: {e}")

def main():
    parser = argparse.ArgumentParser(description="Auto-generate READMEs using AI")
    parser.add_argument("--path", default=".", help="Root path to scan")
    parser.add_argument("--force", action="store_true", help="Overwrite existing READMEs")
    args = parser.parse_args()

    root_path = Path(args.path).resolve()
    print(f"Scanning {root_path} for potential projects...")
    
    roots = get_project_roots(str(root_path))
    
    if not roots:
        print("No units found needing documentation.")
        return

    print(f"Found {len(roots)} potential project roots.")
    
    if not API_KEY:
        print("Error: GEMINI_API_KEY environment variable not set.")
        sys.exit(1)

    for root in roots:
        generate_readme(root, force=args.force)
        # Add a delay between requests to avoid 429 rate limits
        if not args.force: # Only delay if we are doing a standard run
            print("  Waiting 10s to respect rate limits...", flush=True)
            time.sleep(10)

if __name__ == "__main__":
    main()
