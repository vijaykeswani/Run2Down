#!/usr/bin/python
# -*- Mode: Python; coding: utf-8; indent-tabs-mode: nil; tab-width: 4 -*-
### BEGIN LICENSE
# This file is in the public domain
### END LICENSE

import sys
import os.path
import unittest
sys.path.insert(0, os.path.realpath(os.path.join(os.path.dirname(__file__), "..")))

from run2down import AboutRun2downDialog

class TestExample(unittest.TestCase):
    def setUp(self):
        self.AboutRun2downDialog_members = [
        'AboutDialog', 'AboutRun2downDialog', 'gettext', 'logger', 'logging']

    def test_AboutRun2downDialog_members(self):
        all_members = dir(AboutRun2downDialog)
        public_members = [x for x in all_members if not x.startswith('_')]
        public_members.sort()
        self.assertEqual(self.AboutRun2downDialog_members, public_members)

if __name__ == '__main__':    
    unittest.main()
