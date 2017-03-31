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
from swagger_client.apis.network_api import NETWORKApi


class TestNETWORKApi(unittest.TestCase):
    """ NETWORKApi unit test stubs """

    def setUp(self):
        self.api = swagger_client.apis.network_api.NETWORKApi()

    def tearDown(self):
        pass

    def test_create_network_using_post_using_post(self):
        """
        Test case for create_network_using_post_using_post

        createNetwork
        """
        pass

    def test_delete_network_using_delete_using_delete(self):
        """
        Test case for delete_network_using_delete_using_delete

        deleteNetwork
        """
        pass

    def test_get_all_networks_using_get_using_get(self):
        """
        Test case for get_all_networks_using_get_using_get

        getAllNetworks
        """
        pass

    def test_get_network_using_get_using_get(self):
        """
        Test case for get_network_using_get_using_get

        getNetwork
        """
        pass

    def test_update_network_using_put_using_put(self):
        """
        Test case for update_network_using_put_using_put

        updateNetwork
        """
        pass


if __name__ == '__main__':
    unittest.main()