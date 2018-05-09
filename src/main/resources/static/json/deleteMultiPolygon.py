import json

with open('ID_final.json') as json_data:
    d = json.load(json_data)
new_d = {"type": "FeatureCollection","features":[]}
for i in d["features"]:
    print(i["type"])
    print(i["geometry"]["type"]=="MultiPolygon")
    if i["geometry"]["type"] != "MultiPolygon":
        new_d["features"]+=[i]

with open('ID_final.json','w') as outfile:
    d = json.dump(new_d,outfile, indent = 1)