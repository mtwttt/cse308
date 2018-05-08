import json, pymysql
#
def importState(d,name, sid, db):
    cursor = db.cursor()
    try:
        cursor.execute(getSQL(d,name,sid))
        db.commit()
    except:

        db.rollback()
    return

def getSQL(d, name, sid):
    republicanVote = 0
    democratVote = 0
    totalVote = 0
    totalPopulation = 0
    totalRacial = 0
    for i in d["features"]:
        totalPopulation += i["properties"]["POP100"]
        totalRacial += i["properties"]["AV"]
        republicanVote += i["properties"]["PRES08__R"]
        democratVote += i["properties"]["PRES08__D"]
        totalVote += i["properties"]["PRES08__R"]+i["properties"]["PRES08__D"]+i["properties"]["PRES08_MP"]
    overallPartyWin = "d"
    if republicanVote > democratVote:
        overallPartyWin = "r"
    sql = """INSERT INTO State(sid, name, overallPartyWin,republicanStat,
                        democraticSta, year, overallStateVote, totalPopulation, totalAvgRace
                        ) values ( 3, \"Colorado\", \"""" + overallPartyWin + """\",
                        """ + str(float(republicanVote) / float(totalVote)) + """,
                        """ + str(float(democratVote) / float(totalVote)) + """,
                        2008, """ + str(totalVote) + """, """ + str(totalPopulation) + """,
                        """ + str(totalRacial) + """);"""
    return sql

with open("colorado.json") as json_data:
    d = json.load(json_data)
    # addPid(d, fileName)
db = pymysql.connect(host='mysql4.cs.stonybrook.edu', user='zhzou',
                     password='109825816', db='zsyj', charset='utf8mb4', )
importState(d,"Idaho" ,str(2), db)