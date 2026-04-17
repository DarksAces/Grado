# -*- coding: utf-8 -*-
from odoo import models, fields

class AcademyCourse(models.Model):
    _name = 'academy.course'
    _description = 'Internal Training Course'

    name = fields.Char(string='Course Name', required=True)
    description = fields.Text(string='Description')
    duration = fields.Float(string='Duration (Hours)')
    level = fields.Selection([
        ('basic', 'Basic'),
        ('intermediate', 'Intermediate'),
        ('advanced', 'Advanced')
    ], string='Level', default='basic')

class AcademySession(models.Model):
    _name = 'academy.session'
    _description = 'Specific Training Session'
    
    course_id = fields.Many2one('academy.course', string='Course', required=True)
    start_datetime = fields.Datetime(string='Start Date and Time', required=True)
    location = fields.Char(string='Location')
    max_seats = fields.Integer(string='Maximum Seats', default=20)

class AcademyRegistration(models.Model):
    _name = 'academy.registration'
    _description = 'Employee Registration'

    employee_id = fields.Many2one('hr.employee', string='Employee', required=True)
    session_id = fields.Many2one('academy.session', string='Session', required=True)
    score = fields.Float(string='Final Score')
    state = fields.Selection([
        ('draft', 'Draft'),
        ('confirmed', 'Confirmed'),
        ('cancelled', 'Cancelled')
    ], string='Status', default='draft')
