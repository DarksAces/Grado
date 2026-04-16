from odoo import models, fields

class LibraryRental(models.Model):
    _name = 'library.rental'
    _description = 'Asignación de Recurso'

    employee_id = fields.Many2one('hr.employee', string='Empleado', required=True)
    resource_id = fields.Many2one('library.resource', string='Recurso', required=True)
    date_start = fields.Date(string='Fecha de Inicio', default=fields.Date.today)
    date_end = fields.Date(string='Fecha de Fin')
    state = fields.Selection([
        ('draft', 'Borrador'),
        ('in_progress', 'En Curso'),
        ('completed', 'Completado'),
        ('overdue', 'Retrasado')
    ], string='Estado', default='draft')
