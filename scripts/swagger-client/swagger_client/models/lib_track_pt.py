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


class LibTrackPT(object):
    """
    NOTE: This class is auto generated by the swagger code generator program.
    Do not edit the class manually.
    """
    def __init__(self, album_id=None, artist_id=None, description=None, disc_no=None, id=None, length=None, name=None, network_id=None, track_no=None):
        """
        LibTrackPT - a model defined in Swagger

        :param dict swaggerTypes: The key is attribute name
                                  and the value is attribute type.
        :param dict attributeMap: The key is attribute name
                                  and the value is json key in definition.
        """
        self.swagger_types = {
            'album_id': 'int',
            'artist_id': 'int',
            'description': 'str',
            'disc_no': 'int',
            'id': 'int',
            'length': 'int',
            'name': 'str',
            'network_id': 'int',
            'track_no': 'int'
        }

        self.attribute_map = {
            'album_id': 'albumId',
            'artist_id': 'artistId',
            'description': 'description',
            'disc_no': 'discNo',
            'id': 'id',
            'length': 'length',
            'name': 'name',
            'network_id': 'networkId',
            'track_no': 'trackNo'
        }

        self._album_id = album_id
        self._artist_id = artist_id
        self._description = description
        self._disc_no = disc_no
        self._id = id
        self._length = length
        self._name = name
        self._network_id = network_id
        self._track_no = track_no

    @property
    def album_id(self):
        """
        Gets the album_id of this LibTrackPT.

        :return: The album_id of this LibTrackPT.
        :rtype: int
        """
        return self._album_id

    @album_id.setter
    def album_id(self, album_id):
        """
        Sets the album_id of this LibTrackPT.

        :param album_id: The album_id of this LibTrackPT.
        :type: int
        """

        self._album_id = album_id

    @property
    def artist_id(self):
        """
        Gets the artist_id of this LibTrackPT.

        :return: The artist_id of this LibTrackPT.
        :rtype: int
        """
        return self._artist_id

    @artist_id.setter
    def artist_id(self, artist_id):
        """
        Sets the artist_id of this LibTrackPT.

        :param artist_id: The artist_id of this LibTrackPT.
        :type: int
        """

        self._artist_id = artist_id

    @property
    def description(self):
        """
        Gets the description of this LibTrackPT.

        :return: The description of this LibTrackPT.
        :rtype: str
        """
        return self._description

    @description.setter
    def description(self, description):
        """
        Sets the description of this LibTrackPT.

        :param description: The description of this LibTrackPT.
        :type: str
        """

        self._description = description

    @property
    def disc_no(self):
        """
        Gets the disc_no of this LibTrackPT.

        :return: The disc_no of this LibTrackPT.
        :rtype: int
        """
        return self._disc_no

    @disc_no.setter
    def disc_no(self, disc_no):
        """
        Sets the disc_no of this LibTrackPT.

        :param disc_no: The disc_no of this LibTrackPT.
        :type: int
        """
        if disc_no is None:
            raise ValueError("Invalid value for `disc_no`, must not be `None`")

        self._disc_no = disc_no

    @property
    def id(self):
        """
        Gets the id of this LibTrackPT.

        :return: The id of this LibTrackPT.
        :rtype: int
        """
        return self._id

    @id.setter
    def id(self, id):
        """
        Sets the id of this LibTrackPT.

        :param id: The id of this LibTrackPT.
        :type: int
        """

        self._id = id

    @property
    def length(self):
        """
        Gets the length of this LibTrackPT.

        :return: The length of this LibTrackPT.
        :rtype: int
        """
        return self._length

    @length.setter
    def length(self, length):
        """
        Sets the length of this LibTrackPT.

        :param length: The length of this LibTrackPT.
        :type: int
        """
        if length is None:
            raise ValueError("Invalid value for `length`, must not be `None`")

        self._length = length

    @property
    def name(self):
        """
        Gets the name of this LibTrackPT.

        :return: The name of this LibTrackPT.
        :rtype: str
        """
        return self._name

    @name.setter
    def name(self, name):
        """
        Sets the name of this LibTrackPT.

        :param name: The name of this LibTrackPT.
        :type: str
        """
        if name is None:
            raise ValueError("Invalid value for `name`, must not be `None`")

        self._name = name

    @property
    def network_id(self):
        """
        Gets the network_id of this LibTrackPT.

        :return: The network_id of this LibTrackPT.
        :rtype: int
        """
        return self._network_id

    @network_id.setter
    def network_id(self, network_id):
        """
        Sets the network_id of this LibTrackPT.

        :param network_id: The network_id of this LibTrackPT.
        :type: int
        """

        self._network_id = network_id

    @property
    def track_no(self):
        """
        Gets the track_no of this LibTrackPT.

        :return: The track_no of this LibTrackPT.
        :rtype: int
        """
        return self._track_no

    @track_no.setter
    def track_no(self, track_no):
        """
        Sets the track_no of this LibTrackPT.

        :param track_no: The track_no of this LibTrackPT.
        :type: int
        """
        if track_no is None:
            raise ValueError("Invalid value for `track_no`, must not be `None`")

        self._track_no = track_no

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
        if not isinstance(other, LibTrackPT):
            return False

        return self.__dict__ == other.__dict__

    def __ne__(self, other):
        """
        Returns true if both objects are not equal
        """
        return not self == other
