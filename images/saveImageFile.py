import urllib.request
import csv

 
f = open('../cocktail.csv', 'r', encoding='utf-8')
rdr = csv.reader(f)

for line in rdr:
    if line[7] == 'imageURL' or line[7] == '':
        pass
    else:
        fullname = str(line[0]) + ".jpg"
        urllib.request.urlretrieve(line[7], fullname)
