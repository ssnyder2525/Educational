__author__ = 'Stephen Snyder'
__email__ = 'ssnyder2525@yahoo.com'

import unittest
from unrolled_linked_list import UnrolledLinkedList

'''
> Implement your tests here

To run your tests, just run `python tests.py`
'''


class ExampleTest(unittest.TestCase):
    """ Demonstrates how the unittest framework works """

    def test_example(self):
        new_list = UnrolledLinkedList()
        self.assertIsNotNone(new_list)

if __name__ == '__main__':
    unittest.main()
