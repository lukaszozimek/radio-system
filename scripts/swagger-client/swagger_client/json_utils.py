# coding: utf-8

def log(message, object):
    print message + ': \n%s' % object.to_str().replace('\n', '')