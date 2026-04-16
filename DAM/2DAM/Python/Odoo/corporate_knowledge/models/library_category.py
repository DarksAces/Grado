from odoo import models, fields

class LibraryCategory(models.Model):
    _name = 'library.category'
    _description = 'Categoría de Conocimiento'

    name = fields.Char(string='Nombre', required=True)
    description = fields.Text(string='Descripción')
    resource_ids = fields.One2many('library.resource', 'category_id', string='Recursos')
