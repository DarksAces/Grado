
$missingFolders = Get-ChildItem -Path "d:\Documentos\GitHub\Grado" -Recurse -Directory | Where-Object { 
    $_.FullName -notmatch "\.git|node_modules|gradle\\caches|bin|obj|\.vs|\.gradle|build|\.idea|dist|target|Properties|Debug|Release|x64|nbproject|caches|metadata-2" -and
    -not (Test-Path "$($_.FullName)\README.md") -and -not (Test-Path "$($_.FullName)\ReadMe.md")
}

foreach ($folder in $missingFolders) {
    $name = $folder.Name
    $path = $folder.FullName
    $content = ""

    # Categorization logic
    if ($name -match "CSS|Css|Estilos") {
        $content = @"
# 🎨 StyleHub: Custom UI & Aesthetic Orchestration
# 🎨 StyleHub: Orquestación Estética y UI Personalizada

## 📋 Description | Descripción
This specialized directory manages the professional-grade **CSS stylesheets** and visual branding assets for the parent module. It ensures a premium and consistent digital presence across all project interfaces.
"@
    } elseif ($name -match "Fotos|Fotos|Imagenes|Fotos|drawable|mipmap|raw|assets") {
        $content = @"
# 🖼️ AssetHub: High-Density Media Repository
# 🖼️ AssetHub: Repositorio de Medios de Alta Densidad

## 📋 Description | Descripción
This directory orchestrates the optimized **visual assets** (Images, Icons, Branding) for the project. It ensures high performance and visual excellence through a curated repository of media resources.
"@
    } elseif ($name -match "JS|JavaScript|JavaScript") {
        $content = @"
# ⚡ LogicHub: Interactive Scripting Engine
# ⚡ LogicHub: Motor de Scripting Interactivo

## 📋 Description | Descripción
This module manages the professional-grade **JavaScript core** for the project. It orchestrates real-time interactivity, state management, and asynchronous data handling within the web ecosystem.
"@
    } elseif ($name -match "html|html|HTML|Paginas|layout|xml|values") {
        $content = @"
# 🌐 StructureHub: Semantic Web & UI Architecture
# 🌐 StructureHub: Arquitectura Web y UI Semántica

## 📋 Description | Descripción
This directory contains the primary **Structural Templates** (HTML, XML, UI layouts) and configuration values for the module. It ensures a robust, accessible, and SEO-optimized architecture for the digital interface.
"@
    } elseif ($name -match "models|dao|repository|service|controller|dominio|src|main|java|kotlin|kotlin") {
        $content = @"
# 🏛️ ArchitectureLayer: Modular System Component
# 🏛️ ArchitectureLayer: Componente de Sistema Modular

## 📋 Description | Descripción
A specialized **Logic Layer** focusing on a specific architectural concern (Data entities, Persistence, or Business Orchestration). It ensures a clean separation of concerns and maintainable industrial codebase standards.
"@
    } elseif ($name -match "Practica|Actividad|Ejercicio|Examen|T[0-9]|v[0-9]") {
        $content = @"
# 🧪 LabModule: Technical Educational Challenge
# 🧪 LabModule: Desafío Técnico Educativo

## 📋 Description | Descripción
A focused **Technical Laboratory** part of the academic curriculum. It masters a specific set of algorithmic patterns or hardware/software orchestration skills defined in the parent curriculum module.
"@
    } else {
        $content = @"
# 📂 ModuleHub: Standard Project Component
# 📂 ModuleHub: Componente Estándar del Proyecto

## 📋 Description | Descripción
This directory orchestrates a specific functional module within the local ecosystem. It ensures organized resource management and structural integrity for the parent project.
"@
    }

    Set-Content -Path "$path\README.md" -Value $content -Encoding UTF8
    Write-Host "Created README in: $path"
}
