{
    'name': 'Corporate Knowledge Library',
    'version': '1.0',
    'summary': 'Gestión de recursos de conocimiento y préstamos para empleados',
    'description': """
        Módulo para gestionar libros, cursos y material de formación.
        Integrado con el módulo de RRHH (hr).
    """,
    'category': 'Human Resources',
    'author': 'Daniel Garcia Brun',
    'depends': ['base', 'hr'],
    'data': [
        'security/ir.model.access.csv',
        'views/library_resource_views.xml',
        'views/library_category_views.xml',
        'views/library_rental_views.xml',
        'views/library_menus.xml',
    ],
    'installable': True,
    'application': True,
    'license': 'LGPL-3',
}
