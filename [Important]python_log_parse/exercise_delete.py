
# -*- coding: UTF-8 -*-

def gcd(  a,  b):#辗转相除法,原理 (a,b)=(b,a%b) 直到a%b =0，此时b就是最终结果
    """ 最大公约数 greatest commom"""
    while(b!=0):
        r = a % b
        a = b
        b = r
    return  a

#
def lcm(a,b):
    "最小公倍数 least common "
    return a*b/gcd(a,b)

a = int(input()) #input()返回字符串
b = int(input())

print(gcd(a,b))
print(lcm(a,b))
