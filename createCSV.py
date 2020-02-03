import requests
from bs4 import BeautifulSoup
import csv
import requests

material_li = []
cocktail_code_li = []

writer = csv.writer(open('cocktail.csv', 'w', encoding='utf-8', newline=''))
writer.writerow(['cid', 'cname', 'base', 'degree', 'description', 'method', 'material', 'imageURL', 'snack', 'bar'])
headers = {'User-Agent': ':)'}
cid = 1

# Cocktail code 가져오기
for page in range(1, 36):
    url = 'https://terms.naver.com/list.nhn?cid=48182&categoryId=48272&page=%d' %(page)
    response = requests.get(url, headers=headers).text
    text = BeautifulSoup(response, 'html.parser')
    try:
        for num in range(1, 17):
            cocktail_code_li.append(int(str(text.select_one('#content > div.list_wrap > ul > li:nth-child(%d) > div.info_area > div.subject > strong > a:nth-child(1)' %(num))).split('docId=')[1].split('&')[0]))
    except IndexError:
        pass

# cocktail.csv 만들기
for cocktail_code in cocktail_code_li:
    try:
        url = 'https://terms.naver.com/entry.nhn?docId=%d&cid=48182&categoryId=48272' %(cocktail_code)
        response = requests.get(url, headers=headers).text
        text = BeautifulSoup(response, 'html.parser')
        cname = text.select_one('#content > div.section_wrap > div.headword_title > h2').text
        print(cname)
        base = str(text.select_one('#size_ct > div.att_type > div > div.wr_tmp_profile > div > table > tbody > tr:nth-child(2) > td').text).strip()
        if base[-1] == '맛':
            base = ''
        if base:
            if base in material_li:
                pass
            else:
                material_li.append(base)
    except AttributeError:
        base = ''
    try:
        degree = str(text.select_one('#size_ct > div.att_type > div > div.wr_tmp_profile > div > table > tbody > tr:nth-child(4) > td').text).strip()
    except AttributeError:
        degree = ''

    try:
        check_description = text.select_one('#size_ct > p').text
        if '제조방법' in check_description:
            description_li = check_description.split('제조방법')
            description = description_li[0].replace('다.', '다.<br>')
            if '1.' in description_li[1]:
                method = '1.' + description_li[1].split('1.')[1]
                method = method.replace('다.', '다.<br>')
            else:
                method = ''
        else:
            description = check_description.replace('다.', '다.<br>')
            method = ''
    except AttributeError:
        description = ''
        method = ''
    if not description:
        try:
            check_description = text.select_one('#size_ct p').text
            if '제조방법' in check_description:
                description_li = check_description.split('제조방법')
                description = description_li[0].replace('다.', '다.<br>')
                if '1.' in description_li[1]:
                    method = '1.' + description_li[1].split('1.')[1]
                    method = method.replace('다.', '다.\n')
                else:
                    method = ''
            else:
                description = check_description.replace('다.', '다.<br>')
                method = ''
        except AttributeError:
            description = ''
            method = ''
    try:
        check_materials = str(text.select_one('#size_ct > p'))
        if '<strong>재료</strong>' in check_materials:
            check_material_li = check_materials.split('<strong>재료</strong>')[1].split('<br/>')
            for index_material_candidate in range(len(check_material_li)):
                if '1.' in check_material_li[index_material_candidate]:
                    check_material_li = check_material_li[:index_material_candidate]
                    break
                if '|' in check_material_li[index_material_candidate]:
                    material_info = check_material_li[index_material_candidate].split('|')
                    check_material_li[index_material_candidate] = material_info[0]
                    check_material_li.insert(index_material_candidate+1, material_info[1])
            for index_material_candidate in range(len(check_material_li)-1, -1, -1):
                if check_material_li[index_material_candidate] == '' or '<' in check_material_li[index_material_candidate] or len(check_material_li[index_material_candidate]) >= 18 or '칵테일' in check_material_li[index_material_candidate]:
                    check_material_li.pop(index_material_candidate)
            
            material = check_material_li
            for element in material:
                if element in material_li:
                    pass
                else:
                    material_li.append(element)
        else:
            if base:
                material = [base]
            else:
                material = []
    except AttributeError:
        material = []
    try:
        check_img = text.select_one('#innerImage0')
        if not check_img:
            check_img = text.select_one('#size_ct img')
            if not check_img:
                check_img = text.select_one('#content img')
        try:
            imageURL = str(check_img).split('origin_src="')[1].split('"')[0]
        except IndexError:
            imageURL = ''
    except AttributeError:
        imageURL = ''
    snack = ''
    bar = ''
    writer.writerow([cid, cname, base, degree, description, method, material, imageURL, snack, bar])
    cid += 1

# materail.csv 만들기
writer = csv.writer(open('material.csv', 'w', encoding='utf-8', newline=''))
writer.writerow(['mid', 'mname'])
mid = 1
for index_m in range(len(material_li)):
    if ' 또는 ' in material_li[index_m]:
        li = material_li[index_m].split('또는')
        material_li[index_m] = li[0]
        if li[1] not in material_li:
            material_li.append(li[1])
    if '오렌지 슬라이스(또는 레몬 필 약간)' == material_li[index_m]:
        material_li[index_m] = '오렌지 슬라이스'
        if '레몬 필' not in material_li:
            material_li.append('레몬 필')
    writer.writerow([mid, material_li[index_m]])
    mid += 1