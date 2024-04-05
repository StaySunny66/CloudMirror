# 时间 2022.9.25
import hashlib
import psutil
import platform
import json
import re
import time
import os
import requests

ser_data = {}
# 上报数据准备
# HOSTS = "http://127.0.0.1:8080"
HOSTS = "http://api-cm.shilight.cn"
uid = ''



# 高旭阳 2022 10.1 国庆节快乐
# linux 登录日志获取 通过IP数据库 获取系统历史登录ip归属地

country = '中国'
province = '河北'
city = '秦皇岛'
state = 0

strs = os.popen('last root -n 10').read()

# 正则表达式
ip_list = re.findall("root[\s]+pts/.*?[\s]+(.*?)[\s]+(.*?[\s].*?[\s].*?[\s])[\s]+(.*?)", strs)


def get_address(ser, uid):
    state = 0
    address = ''
    headers = {
        'User-Agent': 'Apipost client Runtime/+https://www.apipost.cn/',
        'Authorization': 'APPCODE da0c551dee2c445c9d628bac23660a79',
        'Content-Type': 'text/html; charset=utf-8',
    }

    params = (
        ('ip', ser[0]),
    )

    response = requests.get('https://zjip.market.alicloudapi.com/lifeservice/QueryIpAddr/query', headers=headers,
                            params=params)
    data = json.loads(response.text)
    if data['result']['country'] != country:
        state = 1
    else:
        if data['result']['province'] != province:
            state = 1
        else:
            if data['result']['city'] != city:
                state = 1
    if data['result']['country'] is not None:
        address += data['result']['country']
    if data['result']['province'] is not None:
        address += data['result']['province']
    if data['result']['city'] is not None:
        address += data['result']['city']

    print(address)
    register_Sers(uid, ser[0], ser[1], address, state)
    print(response.text)


def register_Sers(uid, ip, time, address, state):
    headers = {
        'User-Agent': 'Apipost client Runtime/+https://www.apipost.cn/',
    }

    data = {
        'SerUid': uid,
        'ip': ip,
        'time': time,
        'address': address,
        'state': state
    }
    response = requests.post('http://127.0.0.1:8080/ser/login/data', headers=headers, data=data)


def get_data():
    ser_datas = {}
    ser_datas['uid'] = ""
    ser_datas['coreNum'] = psutil.cpu_count()
    ser_datas['cpuUsed'] = psutil.cpu_percent(True)
    ser_datas['ramAll'] = psutil.virtual_memory()[0]
    ser_datas['ramUsed'] = psutil.virtual_memory()[3]
    ser_datas['ramPercent'] = psutil.virtual_memory()[2]
    if platform.system() == 'Windows':
        ser_datas['osType'] = 0
    else:
        ser_datas['osType'] = 1
    return ser_datas


# 注册服务器
def get_uid():
    return hashlib.md5((str(time.time()) + str(requests.get("http://ip.json-json.com/").text)).encode()).hexdigest()[
           0:8]


def save_uid(uid):
    open('config.conf', "w", encoding='utf-8').write(str(uid))


def get_time():
    return int(time.time())


def register_Ser(uid, type):
    data = {
        'ser_UID': uid,
        'name': '',
        'ip': str(requests.get("http://ip.json-json.com/").text),
        'port': 13131,
        'user_id': 'test',
        'type': type,
        'others': ''
    }

    dat = {
        "uid": uid
    }
    print("开始注册服务器")
    r = requests.post(url=HOSTS + "/server/add", data=data)
    s = requests.post(url=HOSTS + "/ser/register", data=dat)
    print(r.text)
    print(s.text)


def upload_detail(uid):
    ser_datas = get_data()
    ser_datas["time"] = int(time.time())
    ser_datas["uid"] = uid
    r = requests.post(url=HOSTS + "/ser/upstate", data=ser_datas)
    print(r.text)


def on_open():
    ser_datas = get_data()
    if not os.path.exists("config.conf"):
        print("不存在 UID")
        uid = get_uid()
        save_uid(uid)
        register_Ser(uid, ser_datas['osType'])
        print(uid)

    uid = open('config.conf', "r", encoding='utf-8').readline()
    print(uid)
    while (True):
        upload_detail(uid)
        for ser in ip_list:
            get_address(ser, uid)
            time.sleep(1)


# print(get_uid())
# save_uid(12334)

on_open()
