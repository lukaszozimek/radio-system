# coding: utf-8

"""
    protone API

    protone API documentation

    OpenAPI spec version: 0.0.1
    
    Generated by: https://github.com/swagger-api/swagger-codegen.git
"""


from __future__ import absolute_import

import os
import sys
import unittest

import swagger_client
from swagger_client.rest import ApiException
from swagger_client.apis.userjwtcontroller_api import UserjwtcontrollerApi


class TestUserjwtcontrollerApi(unittest.TestCase):
    """ UserjwtcontrollerApi unit test stubs """

    def setUp(self):
        self.api = swagger_client.apis.userjwtcontroller_api.UserjwtcontrollerApi()

    def tearDown(self):
        pass

    def test_authorize_using_post(self):
        """
        Test case for authorize_using_post

        authorize
        """
        pass


if __name__ == '__main__':
    unittest.main()