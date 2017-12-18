import xlwt

#将list<list> table 写入新xls
def create_and_write_xls(table,out_xls_name):
    workbook = xlwt.Workbook(encoding = 'utf-8')
    worksheet1 = workbook.add_sheet('worksheet1')
    for i,row in enumerate(table):
        for j,element in enumerate(row):
             worksheet1.write(i,j,element)
    workbook.save(out_xls_name+".xls")