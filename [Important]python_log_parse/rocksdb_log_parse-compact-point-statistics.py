#-*- conding: UTF-8 -*-

import re
import xlwt

#
# main
#


out_xls_name = 'LOG-origin-nocompression-60G'
s_log_path = 'G:\\1-RocksDB\\machine8-home-compaction\\'+ out_xls_name
# regex1 = '\*\*\sDB\sStats[\s\S]*?File\sRead'
regex2 = '\*\*\sDB\sStats[\s\S]*?compaction_job.*?score'
with open(s_log_path,'r') as f:
    s_text = f.read() #字符串类型
    pattern = re.compile(regex2)
    compact_time_list = re.findall(pattern,s_text) #存储每段文本的list， 每段文本只包含第一次出现i@j + n@m 的信息
text_count = len(compact_time_list)

table = [[]]
for i in range(text_count):
    table.append([])
level_nums = 4 # 参与统计的level数量
for i in  range(level_nums):
    table[0].append("L"+str(i+1)+" compact point")
for i in range(level_nums):
    ln_regex = '(\d)@'+str(i+1)+'\s\+' #形如 '(\d)@n\s\+'
    row_num = 1 #标记将要处理table的第row_num行
    for one in compact_time_list:
        tmp_list = re.findall(ln_regex,one)
        if len(tmp_list): #长度不为0
            table[row_num].append(800) #0表示第i层在第row_num个时间点发生了comapction
        else:
            table[row_num].append(0) #0表示第i层在第row_num个时间点没有在发生了comapction
        row_num = row_num+1

#将list<list> table 写入新xls
def create_and_write_xls(table,out_xls_name):
    workbook = xlwt.Workbook(encoding = 'utf-8')
    worksheet1 = workbook.add_sheet('worksheet1')
    for i,row in enumerate(table):
        for j,element in enumerate(row):
            worksheet1.write(i,j,element)
    workbook.save(out_xls_name+".xls")

create_and_write_xls(table,out_xls_name+"-compact-point-statistics")

