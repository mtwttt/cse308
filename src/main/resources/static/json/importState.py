import json, pymysql
#
def importState(d,name, sid, db):
    cursor = db.cursor()
    try:
        cursor.execute(getSQL(d,name,sid))
        db.commit()
    except:
        print("error3")
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
        republicanVote += i["properties"]["PRES_R_08"]
        democratVote += i["properties"]["PRES_D_08"]
        totalVote += i["properties"]["T_08"]
    overallPartyWin = "d"
    if republicanVote > democratVote:
        overallPartyWin = "r"
    sql = """INSERT INTO State(sid, name, overallPartyWin,republicanStat,
                        democraticSta, year, overallStateVote, totalPopulation, totalAvgRace
                        ) values ( str(sid), \"name\", \"""" + overallPartyWin + """\",
                        """ + str(float(republicanVote) / float(totalVote)) + """,
                        """ + str(float(democratVote) / float(totalVote)) + """,
                        2008, """ + str(totalVote) + """, """ + str(totalPopulation) + """,
                        """ + str(totalRacial) + """);"""
    return sql