class A(object):
  def __init__(self, flag):
    self.flag = flag

  def func(self):
    print self.flag

a = A(1)
b = A(2)

callback_a = a.func
callback_b = b.func

print "typeof(callback_a) = {0}".format(type(callback_a))
print "typeof(callback_b) = {0}".format(type(callback_b))

print "typeof(callback_a.__func__) = {0}".format(type(callback_a.__func__))
print "typeof(callback_b.__func__) = {0}".format(type(callback_b.__func__))

print "'callback_a.__func__ is callback_b.__func__'  is {0}".format(callback_a.__func__ is callback_b.__func__)

callback_a()
callback_b()