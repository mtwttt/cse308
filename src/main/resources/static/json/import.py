import json, pymysql
#
with open('colorado.json') as json_data:
    d = json.load(json_data)
db = pymysql.connect(host='mysql4.cs.stonybrook.edu',
                     user='zhzou',
                     password='109825816',
                     db='zsyj',
                     charset='utf8mb4',
                     )
cursor = db.cursor()
for i in d["features"]:
    print(i["pid"])
    pid = str(i["pid"])
    popu =str(i["properties"]["POP100"])
    cdNumber = str(i["properties"]["CONGRESSIO"])
    if cdNumber == "NA":
        cdNumber = "0"
    aLand = str(i["properties"]["ALAND10"])
    aWater = str(i["properties"]["AWATER10"])
    totalVote = str(i["properties"]["PRES08__R"]+i["properties"]["PRES08__D"]+i["properties"]["PRES08_MP"])
    year = str(2008)
    latitude = str(i["properties"]["INTPTLAT10"])
    longtitude = str(i["properties"]["INTPTLON10"])
    rVote = str(i["properties"]["PRES08__R"])
    dVote = str(i["properties"]["PRES08__D"])
    oVote = str(i["properties"]["PRES08_MP"])
    avgRace = str(i["properties"]["AV"])
    sql = """INSERT INTO Precinct( pid, population, cdNumber, aLand, aWater,
                totalVote, year, latitude,
                longtitude, rVote, dVote, oVote, isBorder, avgRace, sid) values (
                """+pid+""",
                """+popu+""",
                """+cdNumber+""",
                """+aLand+""",
                """+aWater+""",
                """+totalVote+""",
                """+year+""",
                """+latitude+""",
                """+longtitude+""",
                """+rVote+""",
                """+dVote+""",
                """+oVote+""",
                0,
                """+avgRace+""",
                3
                );
                """
    try:
        cursor.execute(sql)
        db.commit()
    except:
        print("error1")
        db.rollback()
db.close()