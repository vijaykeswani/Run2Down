# -*- Mode: Python; coding: utf-8; indent-tabs-mode: nil; tab-width: 4 -*-
### BEGIN LICENSE
# This file is in the public domain
### END LICENSE
import subprocess
from locale import gettext as _

from gi.repository import Gtk # pylint: disable=E0611
import logging
logger = logging.getLogger('run2down')

from run2down_lib import Window
from run2down.AboutRun2downDialog import AboutRun2downDialog
from run2down.PreferencesRun2downDialog import PreferencesRun2downDialog

# See run2down_lib.Window.py for more details about how this class works
class Run2downWindow(Window):
    __gtype_name__ = "Run2downWindow"
    password="a"
    
    def finish_initializing(self, builder): # pylint: disable=E1002
        """Set up the main window"""
        super(Run2downWindow, self).finish_initializing(builder)

        self.AboutDialog = AboutRun2downDialog
        self.PreferencesDialog = PreferencesRun2downDialog
        self.button1 = self.builder.get_object("button1");
        self.button2 = self.builder.get_object("button2");
        self.entry1 = self.builder.get_object("entry1");
        self.label2 = self.builder.get_object("label2");
        #self.menu1 = self.builder.get_object("menu1");
        
        #context = self.menu1.get_style_context()
        #context.add_class(Gtk.STYLE_CLASS_PRIMARY_TOOLBAR)
        #context = self.menu1
        context = self.button1.get_style_context()
        context.add_class(Gtk.STYLE_CLASS_PRIMARY_TOOLBAR)
        context = self.button1
        context = self.button2.get_style_context()
        context.add_class(Gtk.STYLE_CLASS_PRIMARY_TOOLBAR)
        context = self.button2
        context = self.entry1.get_style_context()
        context.add_class(Gtk.STYLE_CLASS_PRIMARY_TOOLBAR)
        context = self.entry1     

    def on_button1_clicked(self, widget):
        #print "Hi"
        self.label2.set_text("Sinking.....");
        self.password = self.entry1.get_text()
        subprocess.call(["bash","./script_sink.sh",self.password])
        file=open('currspeed', 'r');
        speed="Your Current speed is " + file.read() + "kbps";
        self.label2.set_text(speed);

    def on_button2_clicked(self, widget):
        #print "Hi"
        self.password = self.entry1.get_text()
        subprocess.call(["bash","./script_emergency.sh",self.password])
        self.label2.set_text("Your current speed is maximum possible");
    
    def on_entry1_activate(self, widget):
        #print "woo"
        self.password = self.entry1.get_text()
        #print self.password
        # Code for other initialization actions should be added here.

