# coding: utf-8

"""
    protone API

    protone API documentation

    OpenAPI spec version: 0.0.1
    
    Generated by: https://github.com/swagger-api/swagger-codegen.git
"""


from pprint import pformat
from six import iteritems
import re


class LibArtistPT(object):
    """
    NOTE: This class is auto generated by the swagger code generator program.
    Do not edit the class manually.
    """
    def __init__(self, description=None, id=None, name=None, network_id=None, type=None):
        """
        LibArtistPT - a model defined in Swagger

        :param dict swaggerTypes: The key is attribute name
                                  and the value is attribute type.
        :param dict attributeMap: The key is attribute name
                                  and the value is json key in definition.
        """
        self.swagger_types = {
            'description': 'str',
            'id': 'int',
            'name': 'str',
            'network_id': 'int',
            'type': 'str'
        }

        self.attribute_map = {
            'description': 'description',
            'id': 'id',
            'name': 'name',
            'network_id': 'networkId',
            'type': 'type'
        }

        self._description = description
        self._id = id
        self._name = name
        self._network_id = network_id
        self._type = type

    @property
    def description(self):
        """
        Gets the description of this LibArtistPT.

        :return: The description of this LibArtistPT.
        :rtype: str
        """
        return self._description

    @description.setter
    def description(self, description):
        """
        Sets the description of this LibArtistPT.

        :param description: The description of this LibArtistPT.
        :type: str
        """

        self._description = description

    @property
    def id(self):
        """
        Gets the id of this LibArtistPT.

        :return: The id of this LibArtistPT.
        :rtype: int
        """
        return self._id

    @id.setter
    def id(self, id):
        """
        Sets the id of this LibArtistPT.

        :param id: The id of this LibArtistPT.
        :type: int
        """

        self._id = id

    @property
    def name(self):
        """
        Gets the name of this LibArtistPT.

        :return: The name of this LibArtistPT.
        :rtype: str
        """
        return self._name

    @name.setter
    def name(self, name):
        """
        Sets the name of this LibArtistPT.

        :param name: The name of this LibArtistPT.
        :type: str
        """
        if name is None:
            raise ValueError("Invalid value for `name`, must not be `None`")

        self._name = name

    @property
    def network_id(self):
        """
        Gets the network_id of this LibArtistPT.

        :return: The network_id of this LibArtistPT.
        :rtype: int
        """
        return self._network_id

    @network_id.setter
    def network_id(self, network_id):
        """
        Sets the network_id of this LibArtistPT.

        :param network_id: The network_id of this LibArtistPT.
        :type: int
        """

        self._network_id = network_id

    @property
    def type(self):
        """
        Gets the type of this LibArtistPT.

        :return: The type of this LibArtistPT.
        :rtype: str
        """
        return self._type

    @type.setter
    def type(self, type):
        """
        Sets the type of this LibArtistPT.

        :param type: The type of this LibArtistPT.
        :type: str
        """
        allowed_values = ["AT_PERSON", "AT_BAND", "AT_CHOIR", "AT_OTHER"]
        if type not in allowed_values:
            raise ValueError(
                "Invalid value for `type` ({0}), must be one of {1}"
                .format(type, allowed_values)
            )

        self._type = type

    def to_dict(self):
        """
        Returns the model properties as a dict
        """
        result = {}

        for attr, _ in iteritems(self.swagger_types):
            value = getattr(self, attr)
            if isinstance(value, list):
                result[attr] = list(map(
                    lambda x: x.to_dict() if hasattr(x, "to_dict") else x,
                    value
                ))
            elif hasattr(value, "to_dict"):
                result[attr] = value.to_dict()
            elif isinstance(value, dict):
                result[attr] = dict(map(
                    lambda item: (item[0], item[1].to_dict())
                    if hasattr(item[1], "to_dict") else item,
                    value.items()
                ))
            else:
                result[attr] = value

        return result

    def to_str(self):
        """
        Returns the string representation of the model
        """
        return pformat(self.to_dict())

    def __repr__(self):
        """
        For `print` and `pprint`
        """
        return self.to_str()

    def __eq__(self, other):
        """
        Returns true if both objects are equal
        """
        if not isinstance(other, LibArtistPT):
            return False

        return self.__dict__ == other.__dict__

    def __ne__(self, other):
        """
        Returns true if both objects are not equal
        """
        return not self == other