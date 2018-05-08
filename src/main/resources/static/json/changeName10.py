import json

with open('ID_final.json') as json_data:
    d = json.load(json_data)
dict = {}
for line in open("Idaho2.txt"):
    new_line = ""
    for i in line:
        if (i >= 'A' and i <= 'Z') or (i >= 'a' and i <= 'z') or (i >= '0' and i <= '9'):
            if (i >= 'A' and i <= 'Z') or (i >= '0' and i <= '9'):
                new_line += ' '
            new_line += i
    s = new_line.split(' ')
    county = int(s[1]+s[2])
    name = s[3]
    if len(s) >4:
        name += ' '+s[4]
    print(int(county))
    print(name)
    dict[county] = name

for i in d["features"]:
    print(i['properties']["NAME10"])
    i['properties']["NAME10"] = dict[i['properties']["COUNTYFP_1"]]
    print(i['properties']["NAME10"])

with open('ID_final.json','w') as outfile:
    d = json.dump(d,outfile, indent = 1)