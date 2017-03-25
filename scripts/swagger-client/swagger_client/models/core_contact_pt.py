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


class CoreContactPT(object):
    """
    NOTE: This class is auto generated by the swagger code generator program.
    Do not edit the class manually.
    """
    def __init__(self, contact=None, contact_type=None, id=None, network_id=None):
        """
        CoreContactPT - a model defined in Swagger

        :param dict swaggerTypes: The key is attribute name
                                  and the value is attribute type.
        :param dict attributeMap: The key is attribute name
                                  and the value is json key in definition.
        """
        self.swagger_types = {
            'contact': 'str',
            'contact_type': 'str',
            'id': 'int',
            'network_id': 'int'
        }

        self.attribute_map = {
            'contact': 'contact',
            'contact_type': 'contactType',
            'id': 'id',
            'network_id': 'networkId'
        }

        self._contact = contact
        self._contact_type = contact_type
        self._id = id
        self._network_id = network_id

    @property
    def contact(self):
        """
        Gets the contact of this CoreContactPT.

        :return: The contact of this CoreContactPT.
        :rtype: str
        """
        return self._contact

    @contact.setter
    def contact(self, contact):
        """
        Sets the contact of this CoreContactPT.

        :param contact: The contact of this CoreContactPT.
        :type: str
        """
        if contact is None:
            raise ValueError("Invalid value for `contact`, must not be `None`")
        if contact is not None and len(contact) > 100:
            raise ValueError("Invalid value for `contact`, length must be less than or equal to `100`")
        if contact is not None and len(contact) < 0:
            raise ValueError("Invalid value for `contact`, length must be greater than or equal to `0`")

        self._contact = contact

    @property
    def contact_type(self):
        """
        Gets the contact_type of this CoreContactPT.

        :return: The contact_type of this CoreContactPT.
        :rtype: str
        """
        return self._contact_type

    @contact_type.setter
    def contact_type(self, contact_type):
        """
        Sets the contact_type of this CoreContactPT.

        :param contact_type: The contact_type of this CoreContactPT.
        :type: str
        """
        allowed_values = ["CT_EMAIL", "CT_PHONE", "CT_OTHER"]
        if contact_type not in allowed_values:
            raise ValueError(
                "Invalid value for `contact_type` ({0}), must be one of {1}"
                .format(contact_type, allowed_values)
            )

        self._contact_type = contact_type

    @property
    def id(self):
        """
        Gets the id of this CoreContactPT.

        :return: The id of this CoreContactPT.
        :rtype: int
        """
        return self._id

    @id.setter
    def id(self, id):
        """
        Sets the id of this CoreContactPT.

        :param id: The id of this CoreContactPT.
        :type: int
        """

        self._id = id

    @property
    def network_id(self):
        """
        Gets the network_id of this CoreContactPT.

        :return: The network_id of this CoreContactPT.
        :rtype: int
        """
        return self._network_id

    @network_id.setter
    def network_id(self, network_id):
        """
        Sets the network_id of this CoreContactPT.

        :param network_id: The network_id of this CoreContactPT.
        :type: int
        """

        self._network_id = network_id

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
        if not isinstance(other, CoreContactPT):
            return False

        return self.__dict__ == other.__dict__

    def __ne__(self, other):
        """
        Returns true if both objects are not equal
        """
        return not self == other
