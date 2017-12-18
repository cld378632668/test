#-*- conding: UTF-8 -*-



import re
import xlwt

#
# main
#


out_xls_name = 'LOG-64s-snapp60G'
s_log_path = 'G:\\1-RocksDB\\machine8-home-compaction\\'+ out_xls_name
regex1 = '\*\*\sDB\sStats[\s\S]*?File\sRead'
with open(s_log_path,'r') as f:
    s_text = f.read() #字符串类型
    pattern = re.compile(regex1)
    compact_stat_list = re.findall(pattern,s_text)

#构建table
table = [[]] #二维数组
suffix_of_table_head_part1_l = ["File Nums", "Size(MB)", "Score", "W-Amp", "Avg(sec)","Compact Point"]
table_head_part2_1ist = ["ops/sec", "interval write ingest(MB/s)","Interval stall(%)"]
for column_name in  suffix_of_table_head_part1_l:
    for i in range(7):
        table[0].append("L"+str(i)+" "+column_name)
for item in table_head_part2_1ist:
    table[0].append(item)

regex ='L\d\s*(\d+)/\d+\s*(\d+\.?\d*\s\w+)\s*(\d+\.?\d*)\s*\d+\.?\d*\s*\d+\.?\d*\s*\d+\.?\d*\s*\d+\.?\d*\s*\d+\.?\d*\s*\d+\.?\d*\s*(\d+\.?\d*)\s*\d+\.?\d*\s*\d+\.?\d*\s*\d+\s*\d+\s*(\d+\.?\d*)\s'
for one_compact_stat_str in compact_stat_list:
    each_level_info_tuple_list = re.findall(regex,one_compact_stat_str) # [ <class 'tuple'>: ( '4', '18.82 MB', '1.0', '1.0', '0.183') ] 一个tuple 5个元素
    nums_in_a_tuple = 5
    #构建table中的一行
    row = []
    for j_int in range(nums_in_a_tuple): #
        for each_level_info_tuple in each_level_info_tuple_list:
            if j_int == 1:
                tmp_list =each_level_info_tuple[j_int].split(" ")
                size = float(tmp_list[0])
                unit = tmp_list[1]
                if unit == 'GB':
                    size = size * 1000
                row.append(size)
            else:
                row.append(float(each_level_info_tuple[j_int]))
        for i in range( 7- len(each_level_info_tuple_list)):
            row.append(0)
    table.append(row)
    #结束构建table中的一行

workbook = xlwt.Workbook(encoding = 'utf-8')
worksheet1 = workbook.add_sheet('worksheet1')
for i,row in enumerate(table):
    for j,element in enumerate(row):
        worksheet1.write(i,j,element)
workbook.save(out_xls_name+".xls")
i=0
# for row in ta


i = 1