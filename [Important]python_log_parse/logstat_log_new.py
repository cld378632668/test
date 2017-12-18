# -*- coding: UTF-8 -*-
"""LOG日志处理"""

import re
import xlwt
import datetime
# FILLE_NAME = '20171018-4s'
# FILLE_NAME = 'LOG_rocksdb_origin-ycsb_1G_50G_onlyrun_cld'
# FILLE_NAME = 'LOG-4s-nocompression'
FILLE_NAME = 'LOG_slot_optimal_default_ycsb_run_60G'
FILLE_NAME = r'G:\1-RocksDB\Slot_Optimal实验\LOG_slot_optimal-nocompression-ycsb_insert_60G'

def find(regex, text):
    """查找正则表达式匹配结果"""
    pattern = re.compile(regex, re.DOTALL + re.MULTILINE)
    return pattern.findall(text)

def get_textlist(path, regex):
    """将log按照时间间隔分段"""
    with open(path, 'r') as fread:
        text = fread.read()
        textlist = re.split(regex, text)
    del textlist[0]
    return textlist

def get_title(table, level_name_list, db_name_list):
    """获得表头"""
    for name in  level_name_list:
        for i in range(8):
            table[0].append("L"+str(i)+" "+name)
    for name in db_name_list:
        table[0].append(name)

def get_row(text):
    """获得表的每一行的值"""
    #              Level   Files       Size               Score         Read(GB)
    regex_line = r'L(\d)\s*(\d+)/\d+\s*(\d+\.?\d*\s\w+)\s*(\d+\.?\d*)\s*\d+\.?\d*\s*'
    #               Rn(GB)      Rnp1(GB)    Write(GB)   Wnew(GB)    Moved(GB)   W-Amp
    regex_line += r'\d+\.?\d*\s*\d+\.?\d*\s*\d+\.?\d*\s*\d+\.?\d*\s*\d+\.?\d*\s*(\d+\.?\d*)\s*'
    #               Rd(MB/s)    Wr(MB/s)    Comp(sec)Comp(cnt)Avg(sec)
    regex_line += r'\d+\.?\d*\s*\d+\.?\d*\s*\d+\s*\d+\s*(\d+\.?\d*)\s*'

    regex_line = r'L(\d)\s*(\d+)/\d+\s*(\d+\.?\d*\s\w+)\s*(\d+\.?\d*)\s*\d+\.?\d*\s*\d+\.?\d*\s*\d+\.?\d*\s*\d+\.?\d*\s*\d+\.?\d*\s*\d+\.?\d*\s*(\d+\.?\d*)\s*\d+\.?\d*\s*\d+\.?\d*\s*\d+\s*\d+\s*(\d+\.?\d*)\s*'
    level_list = find(regex_line, text)  #返回 [[层号,files,size,score,w-amp,avg(sec)]]
    # 返回 interval compaction: [[写入量(GB)，写入速率(MB/s)]]
    # inter_com = find(r'Interval\scompaction:\s(\d+\.?\d*)\sGB\swrite,\
    # \s(\d+\.?\d*)\sMB/s\swrite', text)
    time_detail = find(r'\d\d\d\d/\d\d/\d\d-(\d\d:\d\d:\d\d)\.\d+.*?\n\*\* DB Stats \*\*', text)
    time_sec = find(r'Uptime\(secs\):\s(\d+\.?\d*)\stotal', text)
    #返回interval write ingest速率（MB/s）
    int_write = find(r'Interval\swrites.*?ingest:\s\d+\.?\d*\s\w*,\s(\d+\.?\d*)\sMB/s', text)
    #返回前台写入停顿频率
    int_stall = find(r'Interval\sstall:\s\d\d:\d\d:\d+\.?\d*\sH:M:S,\s(\d+\.?\d*)\spercent', text)
    if len(level_list) <= 0 or len(int_write) <= 0 or len(int_stall) <= 0:
        print("can't find")
        return [-1]*(5*8+3)
    num_in_l = (len(level_list[0])-1)
    level = [[-1 for j in range(8)] for i in range(num_in_l)]
    for one in level_list:
        for i in range(num_in_l):
            if i == 0: #files num 为整型
                level[i][int(one[0])] = int(one[i+1])
            elif i == 1:        #对size不同单位，如MB、GB统一转换为MB
                size_list = one[i+1].split(" ")
                if size_list[1] == "GB":
                    level[i][int(one[0])] = float(size_list[0])*1024
                elif size_list[1] == "MB":
                    level[i][int(one[0])] = float(size_list[0])
                else:
                    pass
            else:  #其他为浮点型
                level[i][int(one[0])] = float(one[i+1])
    row = []
    for i in  level:
        for j in i:
            row.append(j)
    for i in int_write:
        row.append(float(i))
    for i in int_stall:
        row.append(float(i))
    row.append(float(time_sec[0]))
    row.append(time_detail[0])
    return row

def get_table(textlist):
    """将log分段的表转换为table"""
    table = [[]]
    level_name_list = ["Files", "Size (MB)", "score", "w-amp", "avg(sec)"]
    db_name_list = ["interval write ingest(MB/s)", "Interval stall(%)", "time sec", "time_detail"]
    get_title(table, level_name_list, db_name_list)
    for text in textlist:
        row = get_row(text)
        if row:
            table.append(row)
    return table

def table2xls(table, xlsname):
    """将table写入excel"""
    xlswb = xlwt.Workbook(encoding='utf-8')
    xlsws = xlswb.add_sheet('test')
    for i, row in enumerate(table):
        for j, one in enumerate(row):
            xlsws.write(i, j, one)
    xlswb.save(xlsname)

# 处理统计表
# 将日志分段，按照------- DUMPING STATS -------
beginTime = datetime.datetime.now()

TEXT_LIST = get_textlist(FILLE_NAME, r'------- DUMPING STATS -------')
TABLE = get_table(TEXT_LIST)
table2xls(TABLE, FILLE_NAME + ".xls")
endTime = datetime.datetime.now()
print("The script uses "+(str)(endTime - beginTime))

# # 处理时间
# C_TABLE = []
# C_TABLE.append(["time", "from level1", "from level2", "to level", "score"])
# with open(FILLE_NAME + ".log", 'r') as f:
#     for line in f:
#         if re.match(r'.*?Compacting\s\d@\d\s\+\s\d@\d\sfiles\sto.*?,\sscore.*?', line):
#             print(line)
#             c_time = re.search(r'\d\d:\d\d:\d\d', line)
#             [(c_l1, c_l2)] = re.findall(r'\d@(\d) \+ \d@(\d)', line)
#             c_l = re.search(r'L(\d)', line)
#             [c_score] = re.findall(r'score (\d+\.\d+)', line)
#             C_TABLE.append([c_time.group(0), c_l1, c_l2, c_l.group(0), c_score])
# table2xls(C_TABLE, "exactly time.xls")
