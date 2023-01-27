import requests
from urllib.request import urlretrieve

url = "https://store.tildacdn.com/api/getproductslist/?storepartuid=542324870111&recid=191920665&c=1674836262936"

with open("local.properties", "r") as file:
    line = [i for i in file.readlines() if "imgs_path" in i][0]
img_path = line[line.index('"') + 1:-1]

data = requests.get(url).json()["products"]
alphabet = "&=#\"0123456789abcdefghijklmnopqrstuvwxyz></-;():"
rooms = ["Indoor", "Outdoor", "Garden", "Office"]
list_json = []
for index, plant in enumerate(data):
    id = index
    room = rooms[index % len(rooms)]
    title = plant["title"]
    res = ""
    for i in title:
        if i.isalpha() or i == " ":
            res += i
        else:
            break
    title = res
    price = float(plant["price"])
    url_cover = plant["editions"][0]["img"]
    text = "".join([i for i in plant["text"] if i.lower() not in alphabet]).strip()

    flag_ = False
    res = " "
    for i in text:
        if i.isalpha():
            flag_ = True
        if (i.isalpha() or (i == " " and i != 0 and res[-1] != " ")) or (flag_ and i != " "):
            res += i
    text = res[1:].replace(". . . ", ".")\
        .replace(". . , , , , ", ".")\
        .replace("Информация по уходу за растениями РАЗМЕР Средний Диаметр горшка см х Высота растения см ", "")\
        .replace("Цена указана за непересаженное растение в транспортировочном горшке и субстрате.", "")\
        .replace(" , ,", "")\
        .replace("Если вы хотите, чтобы мы пересадили ваше растение, то, пожалуйста, выберите горшок в разделе каталога . Горшки, кашпо, подставки и добавьте его в ваш заказ.", "")\
        .replace("Если вы хотите, чтобы мы пересадили ваше растение, то, пожалуйста, выберите горшок в разделе каталога . Горшки, кашпо, подставки.", "")\
        .replace(" . , , ", ".")\
        .replace(" , ,", "")\
        .replace("Если вы хотите, чтобы мы пересадили ваше растение, то, пожалуйста, включите в заказ услугу пересадки и добавьте в заказ горшок в разделе каталога . Горшки, кашпо, подставки.", "")\
        .replace("Если вы хотите, чтобы мы пересадили ваше растение, то, пожалуйста, включите в заказ услугу пересадки и добавьте в заказ горшок в разделе каталога . Горшки, кашпо, подставки .", "")\
        .replace("Если вы хотите, чтобы мы пересадили ваше растение, то, пожалуйста, выберите горшок в разделе каталога Горшки, кашпо, подставки и добавьте его в ваш заказ.", "")

    description = text[:text.index("СРЕДА ОБИТАНИЯ")].strip()
    cover_name = f'{img_path}/{id}.jpeg'
    urlretrieve(url_cover, cover_name)
    json = {
        "id": id,
        "title": title,
        "description": description,
        "room": room,
        "price": price,
        "cover": cover_name
    }
    list_json.append(json)
    print(title)

with open("init.json", mode="w", encoding="utf-8") as file:
    file.write(str(list_json).replace("'", "\""))
print("УСЁ !")
