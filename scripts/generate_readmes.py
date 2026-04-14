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

# Carpetas que NUNCA deben tener un README propio
EXCLUDE_DIRS = {
    '.git', 'node_modules', '.github', 'scripts', 'scratch', '__pycache__', 
    'bin', 'obj', 'build', 'dist', 'target', '.idea', '.vs', '.gradle',
    'src', 'CSS', 'Fotos', 'JavaScript', 'Html', 'images', 'Assets', 'Recursos', 
    'js', 'css', 'html', 'fotos', 'fonts', 'img', 'lib', 'vendor', 'db', 'config',
    'sql', 'model', 'view', 'controller', 'dto', 'service', 'impl', 'test', 'res', 'resources'
}

EXTENSIONS = {'.java', '.py', '.html', '.ino', '.cpp', '.c', '.js', '.ts', '.sh', '.bat', '.cs'}

AI_SIGNATURE = "<!-- AI-GENERATED-README -->"

PROMPT_TEMPLATE = """
You are an expert technical writer for premium developer portfolios.
Analyze the directory and code context provided.
Generate a high-quality, professional, and BILINGUAL (English and Spanish) README.md.

### QUALITY RULES:
- If the code context is empty or too generic (less than 10 lines of real logic), do NOT generate a full README. Instead, return the exact string: "SKIP_INSUFFICIENT_CONTEXT".
- Do NOT use generic names like "ModuleHub" or "Standard Component". Use the actual folder name or project context.
- Ensure technical accuracy.

### AESTHETIC GUIDELINES:
1. Use relevant emojis for titles.
2. Structure:
   - # Title (Bilingual)
   - ## 📋 Description | Descripción (Detailed purpose).
   - ## ✨ Key Features | Características Clave (Bullet points).
   - ## 🛠️ Tech Stack | Tecnologías (List).
   - ## 📂 Project Structure | Estructura del Proyecto (Tree).
   - ## 🚀 How to Run | Cómo Ejecutar.

### CRITICAL:
- Return ONLY the content of the README.md file (or SKIP_INSUFFICIENT_CONTEXT).
- No markdown wrappers like ```markdown.

### CONTEXT:
Folder Name: {folder_name}
File List: {file_list}
Code Context:
```
{code_context}
```
"""

def get_project_roots(root_dir):
    project_roots = []
    print("--- [3/3] Escaneando carpetas (esto puede tardar)... ---", flush=True)
    for root, dirs, files in os.walk(root_dir):
        # Filtramos directorios excluidos para no entrar en ellos
        current_dir_name = os.path.basename(root).lower()
        if current_dir_name in EXCLUDE_DIRS:
            dirs[:] = [] # Skip subdirectories if current is excluded
            continue

        dirs[:] = [d for d in dirs if d.lower() not in EXCLUDE_DIRS]
        
        # Solo consideramos directorios que tengan ARCHIVOS de código directamente
        has_source = any(Path(f).suffix.lower() in EXTENSIONS for f in files)
        
        if has_source:
            project_roots.append(Path(root))
    
    print("\n[OK] Scan complete.")
    return project_roots

def generate_readme(folder_path, force=False):
    readme_path = folder_path / "README.md"
    
    # Smart Skip Logic
    if readme_path.exists() and not force:
        try:
            with open(readme_path, 'r', encoding='utf-8', errors='ignore') as f:
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
    
    try:
        files = list(folder_path.iterdir())
        file_list = ", ".join([f.name for f in files])
        
        source_files = [f for f in files if f.suffix.lower() in EXTENSIONS]
        if not source_files:
            return
        
        # Cogemos el archivo más representativo
        main_file = max(source_files, key=lambda f: f.stat().st_size)
    except Exception:
        return

    try:
        with open(main_file, 'r', encoding='utf-8', errors='replace') as f:
            code_context = f.read(6000) 
    except Exception:
        code_context = "No code context available."

    prompt = PROMPT_TEMPLATE.format(
        folder_name=folder_path.name,
        file_list=file_list,
        code_context=code_context
    )

    try:
        retries = 3
        response = None
        for i in range(retries):
            try:
                response = model.generate_content(prompt)
                break
            except exceptions.ResourceExhausted:
                time.sleep((i + 1) * 20)
        
        if not response or not response.text:
            return

        content = response.text.strip()
        
        if "SKIP_INSUFFICIENT_CONTEXT" in content:
            print(f"  !! Discarding (Insufficient context) for {folder_path.name}")
            return

        # Clean wrappers
        if content.startswith("```markdown"): content = content[11:].strip()
        if content.startswith("```"): content = content[3:].strip()
        if content.endswith("```"): content = content[:-3].strip()

        # Add AI Signature + UTF-8 Fix
        content = f"{content}\n\n{AI_SIGNATURE}"

        with open(readme_path, 'w', encoding='utf-8') as f:
            f.write(content)
        
        print(f"DONE: Created README.md in {folder_path.name}")
    except Exception as e:
        print(f"ERROR: {e}")

def main():
    parser = argparse.ArgumentParser(description="Auto-generate READMEs using AI")
    parser.add_argument("--path", default=".", help="Root path to scan")
    parser.add_argument("--force", action="store_true", help="Overwrite existing READMEs")
    args = parser.parse_args()

    root_path = Path(args.path).resolve()
    # No documentaremos la raíz del repo ni carpetas de sistema
    roots = get_project_roots(str(root_path))
    
    if not roots: return

    if not API_KEY:
        print("Error: GEMINI_API_KEY environment variable not set.")
        sys.exit(1)

    for root in roots:
        generate_readme(root, force=args.force)
        time.sleep(5) # Delay suave

if __name__ == "__main__":
    main()
