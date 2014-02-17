{
    "pickUp": {
        "location": {
            "address": {
                "country": "GB",
                "zip": "LE",
                "city": "-"
            }
        },
        "interval": {
            "start": "2014-02-06T08:00:00",
            "end": "2014-02-06T08:00:00"
        },
        "comment": "TEST1"
    },
    "delivery": {
        "location": {
        "address": {
          "name": "TLR FRANCE",
          "description": "TELEROUTE FRANCE",
          "streetNr": "1",
          "street": "RUE ERNEST ET ARMAND PEUGEOT",
          "zip": "59000",
          "city": "LILLE",
          "country": "FR"
        }
        },
        "interval": {
            "start": "2014-02-06T08:00:00+0100",
            "end": "2014-02-06T08:00:00+0100"
        },
        "comment": "TEST1 DELIVERY"
    },
    "freightDescription": {
        "type": "Packaged",
        "dimension": {
            "palletType": "EUR",
	    "palletNb": 2,
            "weight": 24000,
            "length": "13.6",
            "volume": 154
        },
        "vehicle": {
            "required": [
                "Tilt",
                "Box"
            ],
            "forbidden": [
                "Van"
            ],
            "equipment": [
                "Tail_Lift"
            ]
        },
        "temperature": {
            "temperatureControl": false
        }
    },
    "contactIds": {"contactId": ["21"]},
    "contacts": {
        "contact": [
            {
                "gender": "M",
                "firstName": "OMAR",
                "lastName": "Nordine",
                "phoneNumber": "+33 5 23 45 67 89",
                "faxNumber": "+33 1 23 45 67 89",
                "email": ""
            }
        ]
    }
}
