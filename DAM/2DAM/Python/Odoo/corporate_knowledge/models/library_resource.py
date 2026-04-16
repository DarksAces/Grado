from odoo import models, fields

class LibraryResource(models.Model):
    _name = 'library.resource'
    _description = 'Recurso de Conocimiento'

    name = fields.Char(string='Título', required=True)
    category_id = fields.Many2one('library.category', string='Categoría')
    resource_type = fields.Selection([
        ('book', 'Libro'),
        ('ebook', 'E-book'),
        ('online_course', 'Curso Online'),
        ('certification', 'Certificación')
    ], string='Tipo de Recurso', default='book')
    link = fields.Char(string='Enlace/URL')
    rental_ids = fields.One2many('library.rental', 'resource_id', string='Asignaciones')
