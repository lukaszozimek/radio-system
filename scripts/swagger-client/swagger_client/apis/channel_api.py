# coding: utf-8

"""
    protone API

    protone API documentation

    OpenAPI spec version: 0.0.1
    
    Generated by: https://github.com/swagger-api/swagger-codegen.git
"""


from __future__ import absolute_import

import sys
import os
import re

# python 2 and python 3 compatibility library
from six import iteritems

from ..configuration import Configuration
from ..api_client import ApiClient


class CHANNELApi(object):
    """
    NOTE: This class is auto generated by the swagger code generator program.
    Do not edit the class manually.
    Ref: https://github.com/swagger-api/swagger-codegen
    """

    def __init__(self, api_client=None):
        config = Configuration()
        if api_client:
            self.api_client = api_client
        else:
            if not config.api_client:
                config.api_client = ApiClient()
            self.api_client = config.api_client

    def create_channel_using_post_using_post(self, network_shortcut, channel_dto, **kwargs):
        """
        createChannel
        This method makes a synchronous HTTP request by default. To make an
        asynchronous HTTP request, please define a `callback` function
        to be invoked when receiving the response.
        >>> def callback_function(response):
        >>>     pprint(response)
        >>>
        >>> thread = api.create_channel_using_post_using_post(network_shortcut, channel_dto, callback=callback_function)

        :param callback function: The callback function
            for asynchronous request. (optional)
        :param str network_shortcut: networkShortcut (required)
        :param CoreChannelPT channel_dto: channelDTO (required)
        :return: CoreChannelPT
                 If the method is called asynchronously,
                 returns the request thread.
        """
        kwargs['_return_http_data_only'] = True
        if kwargs.get('callback'):
            return self.create_channel_using_post_using_post_with_http_info(network_shortcut, channel_dto, **kwargs)
        else:
            (data) = self.create_channel_using_post_using_post_with_http_info(network_shortcut, channel_dto, **kwargs)
            return data

    def create_channel_using_post_using_post_with_http_info(self, network_shortcut, channel_dto, **kwargs):
        """
        createChannel
        This method makes a synchronous HTTP request by default. To make an
        asynchronous HTTP request, please define a `callback` function
        to be invoked when receiving the response.
        >>> def callback_function(response):
        >>>     pprint(response)
        >>>
        >>> thread = api.create_channel_using_post_using_post_with_http_info(network_shortcut, channel_dto, callback=callback_function)

        :param callback function: The callback function
            for asynchronous request. (optional)
        :param str network_shortcut: networkShortcut (required)
        :param CoreChannelPT channel_dto: channelDTO (required)
        :return: CoreChannelPT
                 If the method is called asynchronously,
                 returns the request thread.
        """

        all_params = ['network_shortcut', 'channel_dto']
        all_params.append('callback')
        all_params.append('_return_http_data_only')
        all_params.append('_preload_content')
        all_params.append('_request_timeout')

        params = locals()
        for key, val in iteritems(params['kwargs']):
            if key not in all_params:
                raise TypeError(
                    "Got an unexpected keyword argument '%s'"
                    " to method create_channel_using_post_using_post" % key
                )
            params[key] = val
        del params['kwargs']
        # verify the required parameter 'network_shortcut' is set
        if ('network_shortcut' not in params) or (params['network_shortcut'] is None):
            raise ValueError("Missing the required parameter `network_shortcut` when calling `create_channel_using_post_using_post`")
        # verify the required parameter 'channel_dto' is set
        if ('channel_dto' not in params) or (params['channel_dto'] is None):
            raise ValueError("Missing the required parameter `channel_dto` when calling `create_channel_using_post_using_post`")


        collection_formats = {}

        resource_path = '/api/network/{networkShortcut}/channel'.replace('{format}', 'json')
        path_params = {}
        if 'network_shortcut' in params:
            path_params['networkShortcut'] = params['network_shortcut']

        query_params = {}

        header_params = {}

        form_params = []
        local_var_files = {}

        body_params = None
        if 'channel_dto' in params:
            body_params = params['channel_dto']
        # HTTP header `Accept`
        header_params['Accept'] = self.api_client.\
            select_header_accept(['application/json'])

        # HTTP header `Content-Type`
        header_params['Content-Type'] = self.api_client.\
            select_header_content_type(['application/json'])

        # Authentication setting
        auth_settings = []

        return self.api_client.call_api(resource_path, 'POST',
                                        path_params,
                                        query_params,
                                        header_params,
                                        body=body_params,
                                        post_params=form_params,
                                        files=local_var_files,
                                        response_type='CoreChannelPT',
                                        auth_settings=auth_settings,
                                        callback=params.get('callback'),
                                        _return_http_data_only=params.get('_return_http_data_only'),
                                        _preload_content=params.get('_preload_content', True),
                                        _request_timeout=params.get('_request_timeout'),
                                        collection_formats=collection_formats)

    def delete_channel_using_delete_using_delete(self, network_shortcut, channel_shortcut, **kwargs):
        """
        deleteChannel
        This method makes a synchronous HTTP request by default. To make an
        asynchronous HTTP request, please define a `callback` function
        to be invoked when receiving the response.
        >>> def callback_function(response):
        >>>     pprint(response)
        >>>
        >>> thread = api.delete_channel_using_delete_using_delete(network_shortcut, channel_shortcut, callback=callback_function)

        :param callback function: The callback function
            for asynchronous request. (optional)
        :param str network_shortcut: networkShortcut (required)
        :param str channel_shortcut: channelShortcut (required)
        :return: None
                 If the method is called asynchronously,
                 returns the request thread.
        """
        kwargs['_return_http_data_only'] = True
        if kwargs.get('callback'):
            return self.delete_channel_using_delete_using_delete_with_http_info(network_shortcut, channel_shortcut, **kwargs)
        else:
            (data) = self.delete_channel_using_delete_using_delete_with_http_info(network_shortcut, channel_shortcut, **kwargs)
            return data

    def delete_channel_using_delete_using_delete_with_http_info(self, network_shortcut, channel_shortcut, **kwargs):
        """
        deleteChannel
        This method makes a synchronous HTTP request by default. To make an
        asynchronous HTTP request, please define a `callback` function
        to be invoked when receiving the response.
        >>> def callback_function(response):
        >>>     pprint(response)
        >>>
        >>> thread = api.delete_channel_using_delete_using_delete_with_http_info(network_shortcut, channel_shortcut, callback=callback_function)

        :param callback function: The callback function
            for asynchronous request. (optional)
        :param str network_shortcut: networkShortcut (required)
        :param str channel_shortcut: channelShortcut (required)
        :return: None
                 If the method is called asynchronously,
                 returns the request thread.
        """

        all_params = ['network_shortcut', 'channel_shortcut']
        all_params.append('callback')
        all_params.append('_return_http_data_only')
        all_params.append('_preload_content')
        all_params.append('_request_timeout')

        params = locals()
        for key, val in iteritems(params['kwargs']):
            if key not in all_params:
                raise TypeError(
                    "Got an unexpected keyword argument '%s'"
                    " to method delete_channel_using_delete_using_delete" % key
                )
            params[key] = val
        del params['kwargs']
        # verify the required parameter 'network_shortcut' is set
        if ('network_shortcut' not in params) or (params['network_shortcut'] is None):
            raise ValueError("Missing the required parameter `network_shortcut` when calling `delete_channel_using_delete_using_delete`")
        # verify the required parameter 'channel_shortcut' is set
        if ('channel_shortcut' not in params) or (params['channel_shortcut'] is None):
            raise ValueError("Missing the required parameter `channel_shortcut` when calling `delete_channel_using_delete_using_delete`")


        collection_formats = {}

        resource_path = '/api/network/{networkShortcut}/channel/{channelShortcut}'.replace('{format}', 'json')
        path_params = {}
        if 'network_shortcut' in params:
            path_params['networkShortcut'] = params['network_shortcut']
        if 'channel_shortcut' in params:
            path_params['channelShortcut'] = params['channel_shortcut']

        query_params = {}

        header_params = {}

        form_params = []
        local_var_files = {}

        body_params = None
        # HTTP header `Accept`
        header_params['Accept'] = self.api_client.\
            select_header_accept(['application/json'])

        # HTTP header `Content-Type`
        header_params['Content-Type'] = self.api_client.\
            select_header_content_type(['application/json'])

        # Authentication setting
        auth_settings = []

        return self.api_client.call_api(resource_path, 'DELETE',
                                        path_params,
                                        query_params,
                                        header_params,
                                        body=body_params,
                                        post_params=form_params,
                                        files=local_var_files,
                                        response_type=None,
                                        auth_settings=auth_settings,
                                        callback=params.get('callback'),
                                        _return_http_data_only=params.get('_return_http_data_only'),
                                        _preload_content=params.get('_preload_content', True),
                                        _request_timeout=params.get('_request_timeout'),
                                        collection_formats=collection_formats)

    def get_all_channels_using_get_using_get(self, network_shortcut, **kwargs):
        """
        getAllChannels
        This method makes a synchronous HTTP request by default. To make an
        asynchronous HTTP request, please define a `callback` function
        to be invoked when receiving the response.
        >>> def callback_function(response):
        >>>     pprint(response)
        >>>
        >>> thread = api.get_all_channels_using_get_using_get(network_shortcut, callback=callback_function)

        :param callback function: The callback function
            for asynchronous request. (optional)
        :param str network_shortcut: networkShortcut (required)
        :return: CoreChannelPT
                 If the method is called asynchronously,
                 returns the request thread.
        """
        kwargs['_return_http_data_only'] = True
        if kwargs.get('callback'):
            return self.get_all_channels_using_get_using_get_with_http_info(network_shortcut, **kwargs)
        else:
            (data) = self.get_all_channels_using_get_using_get_with_http_info(network_shortcut, **kwargs)
            return data

    def get_all_channels_using_get_using_get_with_http_info(self, network_shortcut, **kwargs):
        """
        getAllChannels
        This method makes a synchronous HTTP request by default. To make an
        asynchronous HTTP request, please define a `callback` function
        to be invoked when receiving the response.
        >>> def callback_function(response):
        >>>     pprint(response)
        >>>
        >>> thread = api.get_all_channels_using_get_using_get_with_http_info(network_shortcut, callback=callback_function)

        :param callback function: The callback function
            for asynchronous request. (optional)
        :param str network_shortcut: networkShortcut (required)
        :return: CoreChannelPT
                 If the method is called asynchronously,
                 returns the request thread.
        """

        all_params = ['network_shortcut']
        all_params.append('callback')
        all_params.append('_return_http_data_only')
        all_params.append('_preload_content')
        all_params.append('_request_timeout')

        params = locals()
        for key, val in iteritems(params['kwargs']):
            if key not in all_params:
                raise TypeError(
                    "Got an unexpected keyword argument '%s'"
                    " to method get_all_channels_using_get_using_get" % key
                )
            params[key] = val
        del params['kwargs']
        # verify the required parameter 'network_shortcut' is set
        if ('network_shortcut' not in params) or (params['network_shortcut'] is None):
            raise ValueError("Missing the required parameter `network_shortcut` when calling `get_all_channels_using_get_using_get`")


        collection_formats = {}

        resource_path = '/api/network/{networkShortcut}/channel'.replace('{format}', 'json')
        path_params = {}
        if 'network_shortcut' in params:
            path_params['networkShortcut'] = params['network_shortcut']

        query_params = {}

        header_params = {}

        form_params = []
        local_var_files = {}

        body_params = None
        # HTTP header `Accept`
        header_params['Accept'] = self.api_client.\
            select_header_accept(['application/json'])

        # HTTP header `Content-Type`
        header_params['Content-Type'] = self.api_client.\
            select_header_content_type(['application/json'])

        # Authentication setting
        auth_settings = []

        return self.api_client.call_api(resource_path, 'GET',
                                        path_params,
                                        query_params,
                                        header_params,
                                        body=body_params,
                                        post_params=form_params,
                                        files=local_var_files,
                                        response_type='list[CoreChannelPT]',
                                        auth_settings=auth_settings,
                                        callback=params.get('callback'),
                                        _return_http_data_only=params.get('_return_http_data_only'),
                                        _preload_content=params.get('_preload_content', True),
                                        _request_timeout=params.get('_request_timeout'),
                                        collection_formats=collection_formats)

    def get_channel_using_get_using_get(self, network_shortcut, channel_shortcut, **kwargs):
        """
        getChannel
        This method makes a synchronous HTTP request by default. To make an
        asynchronous HTTP request, please define a `callback` function
        to be invoked when receiving the response.
        >>> def callback_function(response):
        >>>     pprint(response)
        >>>
        >>> thread = api.get_channel_using_get_using_get(network_shortcut, channel_shortcut, callback=callback_function)

        :param callback function: The callback function
            for asynchronous request. (optional)
        :param str network_shortcut: networkShortcut (required)
        :param str channel_shortcut: channelShortcut (required)
        :return: CoreChannelPT
                 If the method is called asynchronously,
                 returns the request thread.
        """
        kwargs['_return_http_data_only'] = True
        if kwargs.get('callback'):
            return self.get_channel_using_get_using_get_with_http_info(network_shortcut, channel_shortcut, **kwargs)
        else:
            (data) = self.get_channel_using_get_using_get_with_http_info(network_shortcut, channel_shortcut, **kwargs)
            return data

    def get_channel_using_get_using_get_with_http_info(self, network_shortcut, channel_shortcut, **kwargs):
        """
        getChannel
        This method makes a synchronous HTTP request by default. To make an
        asynchronous HTTP request, please define a `callback` function
        to be invoked when receiving the response.
        >>> def callback_function(response):
        >>>     pprint(response)
        >>>
        >>> thread = api.get_channel_using_get_using_get_with_http_info(network_shortcut, channel_shortcut, callback=callback_function)

        :param callback function: The callback function
            for asynchronous request. (optional)
        :param str network_shortcut: networkShortcut (required)
        :param str channel_shortcut: channelShortcut (required)
        :return: CoreChannelPT
                 If the method is called asynchronously,
                 returns the request thread.
        """

        all_params = ['network_shortcut', 'channel_shortcut']
        all_params.append('callback')
        all_params.append('_return_http_data_only')
        all_params.append('_preload_content')
        all_params.append('_request_timeout')

        params = locals()
        for key, val in iteritems(params['kwargs']):
            if key not in all_params:
                raise TypeError(
                    "Got an unexpected keyword argument '%s'"
                    " to method get_channel_using_get_using_get" % key
                )
            params[key] = val
        del params['kwargs']
        # verify the required parameter 'network_shortcut' is set
        if ('network_shortcut' not in params) or (params['network_shortcut'] is None):
            raise ValueError("Missing the required parameter `network_shortcut` when calling `get_channel_using_get_using_get`")
        # verify the required parameter 'channel_shortcut' is set
        if ('channel_shortcut' not in params) or (params['channel_shortcut'] is None):
            raise ValueError("Missing the required parameter `channel_shortcut` when calling `get_channel_using_get_using_get`")


        collection_formats = {}

        resource_path = '/api/network/{networkShortcut}/channel/{channelShortcut}'.replace('{format}', 'json')
        path_params = {}
        if 'network_shortcut' in params:
            path_params['networkShortcut'] = params['network_shortcut']
        if 'channel_shortcut' in params:
            path_params['channelShortcut'] = params['channel_shortcut']

        query_params = {}

        header_params = {}

        form_params = []
        local_var_files = {}

        body_params = None
        # HTTP header `Accept`
        header_params['Accept'] = self.api_client.\
            select_header_accept(['application/json'])

        # HTTP header `Content-Type`
        header_params['Content-Type'] = self.api_client.\
            select_header_content_type(['application/json'])

        # Authentication setting
        auth_settings = []

        return self.api_client.call_api(resource_path, 'GET',
                                        path_params,
                                        query_params,
                                        header_params,
                                        body=body_params,
                                        post_params=form_params,
                                        files=local_var_files,
                                        response_type='CoreChannelPT',
                                        auth_settings=auth_settings,
                                        callback=params.get('callback'),
                                        _return_http_data_only=params.get('_return_http_data_only'),
                                        _preload_content=params.get('_preload_content', True),
                                        _request_timeout=params.get('_request_timeout'),
                                        collection_formats=collection_formats)

    def update_channel_using_put_using_put(self, network_shortcut, channel_dto, **kwargs):
        """
        updateChannel
        This method makes a synchronous HTTP request by default. To make an
        asynchronous HTTP request, please define a `callback` function
        to be invoked when receiving the response.
        >>> def callback_function(response):
        >>>     pprint(response)
        >>>
        >>> thread = api.update_channel_using_put_using_put(network_shortcut, channel_dto, callback=callback_function)

        :param callback function: The callback function
            for asynchronous request. (optional)
        :param str network_shortcut: networkShortcut (required)
        :param CoreChannelPT channel_dto: channelDTO (required)
        :return: CoreChannelPT
                 If the method is called asynchronously,
                 returns the request thread.
        """
        kwargs['_return_http_data_only'] = True
        if kwargs.get('callback'):
            return self.update_channel_using_put_using_put_with_http_info(network_shortcut, channel_dto, **kwargs)
        else:
            (data) = self.update_channel_using_put_using_put_with_http_info(network_shortcut, channel_dto, **kwargs)
            return data

    def update_channel_using_put_using_put_with_http_info(self, network_shortcut, channel_dto, **kwargs):
        """
        updateChannel
        This method makes a synchronous HTTP request by default. To make an
        asynchronous HTTP request, please define a `callback` function
        to be invoked when receiving the response.
        >>> def callback_function(response):
        >>>     pprint(response)
        >>>
        >>> thread = api.update_channel_using_put_using_put_with_http_info(network_shortcut, channel_dto, callback=callback_function)

        :param callback function: The callback function
            for asynchronous request. (optional)
        :param str network_shortcut: networkShortcut (required)
        :param CoreChannelPT channel_dto: channelDTO (required)
        :return: CoreChannelPT
                 If the method is called asynchronously,
                 returns the request thread.
        """

        all_params = ['network_shortcut', 'channel_dto']
        all_params.append('callback')
        all_params.append('_return_http_data_only')
        all_params.append('_preload_content')
        all_params.append('_request_timeout')

        params = locals()
        for key, val in iteritems(params['kwargs']):
            if key not in all_params:
                raise TypeError(
                    "Got an unexpected keyword argument '%s'"
                    " to method update_channel_using_put_using_put" % key
                )
            params[key] = val
        del params['kwargs']
        # verify the required parameter 'network_shortcut' is set
        if ('network_shortcut' not in params) or (params['network_shortcut'] is None):
            raise ValueError("Missing the required parameter `network_shortcut` when calling `update_channel_using_put_using_put`")
        # verify the required parameter 'channel_dto' is set
        if ('channel_dto' not in params) or (params['channel_dto'] is None):
            raise ValueError("Missing the required parameter `channel_dto` when calling `update_channel_using_put_using_put`")


        collection_formats = {}

        resource_path = '/api/network/{networkShortcut}/channel'.replace('{format}', 'json')
        path_params = {}
        if 'network_shortcut' in params:
            path_params['networkShortcut'] = params['network_shortcut']

        query_params = {}

        header_params = {}

        form_params = []
        local_var_files = {}

        body_params = None
        if 'channel_dto' in params:
            body_params = params['channel_dto']
        # HTTP header `Accept`
        header_params['Accept'] = self.api_client.\
            select_header_accept(['application/json'])

        # HTTP header `Content-Type`
        header_params['Content-Type'] = self.api_client.\
            select_header_content_type(['application/json'])

        # Authentication setting
        auth_settings = []

        return self.api_client.call_api(resource_path, 'PUT',
                                        path_params,
                                        query_params,
                                        header_params,
                                        body=body_params,
                                        post_params=form_params,
                                        files=local_var_files,
                                        response_type='CoreChannelPT',
                                        auth_settings=auth_settings,
                                        callback=params.get('callback'),
                                        _return_http_data_only=params.get('_return_http_data_only'),
                                        _preload_content=params.get('_preload_content', True),
                                        _request_timeout=params.get('_request_timeout'),
                                        collection_formats=collection_formats)
