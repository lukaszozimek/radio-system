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
from swagger_client.apis.dictionary_api import DICTIONARYApi


class TestDICTIONARYApi(unittest.TestCase):
    """ DICTIONARYApi unit test stubs """

    def setUp(self):
        self.api = swagger_client.apis.dictionary_api.DICTIONARYApi()

    def tearDown(self):
        pass

    def test_create_area_using_post_using_post(self):
        """
        Test case for create_area_using_post_using_post

        createArea
        """
        pass

    def test_create_campaing_status_using_post_using_post(self):
        """
        Test case for create_campaing_status_using_post_using_post

        createCampaingStatus
        """
        pass

    def test_create_country_using_post_using_post(self):
        """
        Test case for create_country_using_post_using_post

        createCountry
        """
        pass

    def test_create_crm_stage_using_post_using_post(self):
        """
        Test case for create_crm_stage_using_post_using_post

        createCrmStage
        """
        pass

    def test_create_crm_task_status_using_post_using_post(self):
        """
        Test case for create_crm_task_status_using_post_using_post

        createTaskStatus
        """
        pass

    def test_create_currency_using_post_using_post(self):
        """
        Test case for create_currency_using_post_using_post

        createCurrency
        """
        pass

    def test_create_invoice_status_using_post_using_post(self):
        """
        Test case for create_invoice_status_using_post_using_post

        createInvoiceStatus
        """
        pass

    def test_create_lead_status_using_post_using_post(self):
        """
        Test case for create_lead_status_using_post_using_post

        createLeadStatus
        """
        pass

    def test_create_leadsource_using_post_using_post(self):
        """
        Test case for create_leadsource_using_post_using_post

        createLeadsource
        """
        pass

    def test_create_order_status_using_post_using_post(self):
        """
        Test case for create_order_status_using_post_using_post

        createTax
        """
        pass

    def test_create_person_using_post_using_post(self):
        """
        Test case for create_person_using_post_using_post

        createPersonEntity
        """
        pass

    def test_create_property_key_using_post_using_post(self):
        """
        Test case for create_property_key_using_post_using_post

        createPropertyKey
        """
        pass

    def test_create_range_using_post_using_post(self):
        """
        Test case for create_range_using_post_using_post

        createRange
        """
        pass

    def test_create_size_using_post_using_post(self):
        """
        Test case for create_size_using_post_using_post

        createSize
        """
        pass

    def test_create_tax_using_post_using_post(self):
        """
        Test case for create_tax_using_post_using_post

        createTax
        """
        pass

    def test_delete_area_using_delete_using_delete(self):
        """
        Test case for delete_area_using_delete_using_delete

        deleteArea
        """
        pass

    def test_delete_campaing_status_using_delete_using_delete(self):
        """
        Test case for delete_campaing_status_using_delete_using_delete

        deleteCampaingStatus
        """
        pass

    def test_delete_country_using_delete_using_delete(self):
        """
        Test case for delete_country_using_delete_using_delete

        deletePerson
        """
        pass

    def test_delete_crm_stage_using_delete_using_delete(self):
        """
        Test case for delete_crm_stage_using_delete_using_delete

        deleteCrmStage
        """
        pass

    def test_delete_crm_task_status_using_delete_using_delete(self):
        """
        Test case for delete_crm_task_status_using_delete_using_delete

        deleteCrmTaskStatus
        """
        pass

    def test_delete_currency_using_delete_using_delete(self):
        """
        Test case for delete_currency_using_delete_using_delete

        deleteCurrency
        """
        pass

    def test_delete_industry_using_delete_using_delete(self):
        """
        Test case for delete_industry_using_delete_using_delete

        deleteIndustry
        """
        pass

    def test_delete_invoice_status_using_delete_using_delete(self):
        """
        Test case for delete_invoice_status_using_delete_using_delete

        deleteInvoiceStatus
        """
        pass

    def test_delete_lead_status_using_delete_using_delete(self):
        """
        Test case for delete_lead_status_using_delete_using_delete

        deleteLeadstatus
        """
        pass

    def test_delete_leadsource_using_delete_using_delete(self):
        """
        Test case for delete_leadsource_using_delete_using_delete

        deleteLeadsource
        """
        pass

    def test_delete_order_status_using_delete_using_delete(self):
        """
        Test case for delete_order_status_using_delete_using_delete

        deleteTax
        """
        pass

    def test_delete_person_using_delete_using_delete(self):
        """
        Test case for delete_person_using_delete_using_delete

        deletePerson
        """
        pass

    def test_delete_property_key_using_delete_using_delete(self):
        """
        Test case for delete_property_key_using_delete_using_delete

        deletePropertyKey
        """
        pass

    def test_delete_range_using_delete_using_delete(self):
        """
        Test case for delete_range_using_delete_using_delete

        deleteRange
        """
        pass

    def test_delete_size_using_delete_using_delete(self):
        """
        Test case for delete_size_using_delete_using_delete

        deleteSize
        """
        pass

    def test_delete_tax_using_delete_using_delete(self):
        """
        Test case for delete_tax_using_delete_using_delete

        deleteTax
        """
        pass

    def test_get_all_area_using_get_using_get(self):
        """
        Test case for get_all_area_using_get_using_get

        getAllArea
        """
        pass

    def test_get_all_campaing_status_using_get_using_get(self):
        """
        Test case for get_all_campaing_status_using_get_using_get

        getAllCampaingStatus
        """
        pass

    def test_get_all_countries_using_get_using_get(self):
        """
        Test case for get_all_countries_using_get_using_get

        getAllCountries
        """
        pass

    def test_get_all_crm_stages_using_get_using_get(self):
        """
        Test case for get_all_crm_stages_using_get_using_get

        getAllCrmStages
        """
        pass

    def test_get_all_crm_task_status_using_get_using_get(self):
        """
        Test case for get_all_crm_task_status_using_get_using_get

        getAllCrmTaskStatus
        """
        pass

    def test_get_all_currency_using_get_using_get(self):
        """
        Test case for get_all_currency_using_get_using_get

        getAllCurrency
        """
        pass

    def test_get_all_industries_using_get_using_get(self):
        """
        Test case for get_all_industries_using_get_using_get

        getAllIndustries
        """
        pass

    def test_get_all_invoice_status_using_get_using_get(self):
        """
        Test case for get_all_invoice_status_using_get_using_get

        getAllInvoiceStatus
        """
        pass

    def test_get_all_lead_status_using_get_using_get(self):
        """
        Test case for get_all_lead_status_using_get_using_get

        getAllLeadStatus
        """
        pass

    def test_get_all_leadsource_using_get_using_get(self):
        """
        Test case for get_all_leadsource_using_get_using_get

        getAllLeadSource
        """
        pass

    def test_get_all_order_status_using_get_using_get(self):
        """
        Test case for get_all_order_status_using_get_using_get

        getAllTaxes
        """
        pass

    def test_get_all_people_using_get_using_get(self):
        """
        Test case for get_all_people_using_get_using_get

        getAllPeople
        """
        pass

    def test_get_all_property_keys_using_get_using_get(self):
        """
        Test case for get_all_property_keys_using_get_using_get

        getAllPropertyKeys
        """
        pass

    def test_get_all_range_using_get_using_get(self):
        """
        Test case for get_all_range_using_get_using_get

        getAllRange
        """
        pass

    def test_get_all_size_using_get_using_get(self):
        """
        Test case for get_all_size_using_get_using_get

        getAllSize
        """
        pass

    def test_get_all_taxes_using_get_using_get(self):
        """
        Test case for get_all_taxes_using_get_using_get

        getAllTaxes
        """
        pass

    def test_get_area_using_get_using_get(self):
        """
        Test case for get_area_using_get_using_get

        getArea
        """
        pass

    def test_get_campaing_status_using_get_using_get(self):
        """
        Test case for get_campaing_status_using_get_using_get

        getCampaingStatus
        """
        pass

    def test_get_country_using_get_using_get(self):
        """
        Test case for get_country_using_get_using_get

        getPerson
        """
        pass

    def test_get_crm_stage_using_get_using_get(self):
        """
        Test case for get_crm_stage_using_get_using_get

        getCrmStage
        """
        pass

    def test_get_crm_task_status_using_get_using_get(self):
        """
        Test case for get_crm_task_status_using_get_using_get

        getCrmTaskStatus
        """
        pass

    def test_get_currency_using_get_using_get(self):
        """
        Test case for get_currency_using_get_using_get

        getCurrency
        """
        pass

    def test_get_industry_using_get_using_get(self):
        """
        Test case for get_industry_using_get_using_get

        getIndustry
        """
        pass

    def test_get_invoice_status_using_get_using_get(self):
        """
        Test case for get_invoice_status_using_get_using_get

        getInvoiceStatus
        """
        pass

    def test_get_lead_source_using_get_using_get(self):
        """
        Test case for get_lead_source_using_get_using_get

        getLeadStatus
        """
        pass

    def test_get_lead_status_using_get_using_get(self):
        """
        Test case for get_lead_status_using_get_using_get

        getLeadStatus
        """
        pass

    def test_get_order_status_using_get_using_get(self):
        """
        Test case for get_order_status_using_get_using_get

        getTax
        """
        pass

    def test_get_person_using_get_using_get(self):
        """
        Test case for get_person_using_get_using_get

        getPerson
        """
        pass

    def test_get_property_key_using_get_using_get(self):
        """
        Test case for get_property_key_using_get_using_get

        getPropertyKey
        """
        pass

    def test_get_range_using_get_using_get(self):
        """
        Test case for get_range_using_get_using_get

        getRange
        """
        pass

    def test_get_size_using_get_using_get(self):
        """
        Test case for get_size_using_get_using_get

        getSize
        """
        pass

    def test_get_tax_using_get_using_get(self):
        """
        Test case for get_tax_using_get_using_get

        getTax
        """
        pass

    def test_update_area_using_put_using_put(self):
        """
        Test case for update_area_using_put_using_put

        updateArea
        """
        pass

    def test_update_campaing_status_using_put_using_put(self):
        """
        Test case for update_campaing_status_using_put_using_put

        updateCampaingStatus
        """
        pass

    def test_update_country_using_put_using_put(self):
        """
        Test case for update_country_using_put_using_put

        updateCountry
        """
        pass

    def test_update_crm_stage_using_put_using_put(self):
        """
        Test case for update_crm_stage_using_put_using_put

        updateCrmStage
        """
        pass

    def test_update_crm_task_status_using_put_using_put(self):
        """
        Test case for update_crm_task_status_using_put_using_put

        updateTaskStatus
        """
        pass

    def test_update_currency_using_put_using_put(self):
        """
        Test case for update_currency_using_put_using_put

        updateCurrency
        """
        pass

    def test_update_invoice_status_using_put_using_put(self):
        """
        Test case for update_invoice_status_using_put_using_put

        updateInvoiceStatus
        """
        pass

    def test_update_lead_source_using_put_using_put(self):
        """
        Test case for update_lead_source_using_put_using_put

        updateLeadSource
        """
        pass

    def test_update_order_status_using_put_using_put(self):
        """
        Test case for update_order_status_using_put_using_put

        updateTax
        """
        pass

    def test_update_person_using_put_using_put(self):
        """
        Test case for update_person_using_put_using_put

        updatePerson
        """
        pass

    def test_update_property_key_using_put_using_put(self):
        """
        Test case for update_property_key_using_put_using_put

        updatePropertyKey
        """
        pass

    def test_update_range_using_put_using_put(self):
        """
        Test case for update_range_using_put_using_put

        updateRange
        """
        pass

    def test_update_size_using_put_using_put(self):
        """
        Test case for update_size_using_put_using_put

        updateSize
        """
        pass

    def test_update_tax_using_put_using_put(self):
        """
        Test case for update_tax_using_put_using_put

        updateTax
        """
        pass

    def test_updatelead_status_using_put_using_put(self):
        """
        Test case for updatelead_status_using_put_using_put

        updateLeadStatus
        """
        pass


if __name__ == '__main__':
    unittest.main()
