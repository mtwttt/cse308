import json

with open('colorado.json') as json_data:
    d = json.load(json_data)


dict_c = {}

prev = ""
for line in open("CO2.txt"):
    if prev == "":
        prev = int(line)
    else:
        dict_c[prev] = line[:-1]
        prev = ""
print(dict_c)
dict={}
for line in open("CO_cd.txt"):
    new_line = ""
    for i in line:
        if  (i>='A' and i<='Z') or (i>='a' and i<='z') or( i>='0' and i<='9'):
            if (i>='A' and i<='Z') or( i>='0' and i<='9'):
                new_line += ' '
            new_line += i
    s = new_line.split(' ')
    cd_number = s[-1]
    name = ""
    for i in range(1,len(s)-1):
        name += s[i]+' '
    name = name[:-1]
    print(name)
    print(cd_number)
    dict[name] = cd_number
print(dict)
dict_c[14] = "2"
for i in d["features"]:
    print(i['properties']["NAME10"])
    check = False
    for j in list(dict.keys()):
        if dict_c[i['properties']['COUNTYFP_1']] in j or j in dict_c[i['properties']['COUNTYFP_1']]:
            i['properties']['CONGRESSIO'] = dict[j]
            check = True
    if not check:
        i['properties']['CONGRESSIO'] = "2"

for i in d["features"]:
    print(i['properties']["NAME10"])
    print(i['properties']['CONGRESSIO'])

with open('colorado.json','w') as outfile:
    d = json.dump(d,outfile, indent = 1)