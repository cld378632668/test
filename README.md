
# Notes Metos 有道云笔记  印象笔记  直接往github上写

文件等中转到另一个电脑端

# JavaUtil/Scripts

【废弃】[Important]JavaApiUtilOrExample文件
整个文件夹 用https://github.com/cld378632668/Tools中的'utils模块'替换

## 常用脚本

批量处理文件名

批量处理多个文件中的变量（待补充）
批量修改多个文件中的变量名，为这些变量添加所在文件名为后缀，能否用linux命令或python简单实现?  java复杂实现也行？




# 2

CSVHelper.py 对目下的csv文件批量操作

# 3 

## check:
place   上研？ 1J 南，2H 南 双显示器  2F-14/16 单显示器
wecode is okay
demo exercise 
memo in clion

## map

增：
map.insert(std::pair<string,valueType>("key","value"));
map.insert(it,std::pair<string,valueType>("key","value"))

it = map.find("key");

count计数接口：
map中本身key就是唯一的，所以包含这返回1，不包含则返回0。

map： map内部实现了一个红黑树，所以会自动改变元素的顺序？ 之后遍历元素时的顺序，与插入时的顺序不一样？。该结构具有自动排序的功能，因此map内部的所有元素都是有序的，红黑树的每一个节点都代表着map的一个元素，因此，对于map进行的查找，删除，添加等一系列的操作都相当于是对红黑树进行这样的操作，故红黑树的效率决定了map的效率。
unordered_map: unordered_map内部实现了一个哈希表，因此其元素的排列顺序是杂乱的，无序的

## vector
vector<int> v;
 
vector::iterator it = v.begin();

while(;it != v.end(); it++) {
  int tmp = *it；
}

vector.insert()?
vector.()?
