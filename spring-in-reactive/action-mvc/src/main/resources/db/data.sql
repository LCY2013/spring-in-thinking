INSERT INTO STOCK VALUES('TSLA', '特斯拉');
INSERT INTO STOCK VALUES('AMZN', '亚马逊');
INSERT INTO STOCK VALUES('APPL', '苹果');
INSERT INTO STOCK VALUES('SBUX', '星巴克');
INSERT INTO STOCK VALUES('MSFT', '微软');
INSERT INTO STOCK VALUES('PYPL', '贝宝');
INSERT INTO STOCK VALUES('GOOG', '谷歌');
INSERT INTO STOCK VALUES('BABA', '阿里巴巴');
INSERT INTO STOCK VALUES('INTC', '英特尔');
INSERT INTO STOCK VALUES('NFLX', '奈飞');
INSERT INTO STOCK VALUES('KO', '可口可乐');
INSERT INTO STOCK VALUES('XIACY', '小米');
INSERT INTO STOCK VALUES('AMD', 'AMD Yes!');


INSERT INTO STOCK_SUBSCRIPTION VALUES(10001, 'tester@qq.com', 'TSLA');
INSERT INTO STOCK_SUBSCRIPTION VALUES(10002, 'tester@qq.com', 'AMZN');

INSERT INTO APP_ROLE VALUES(10001, 'ROLE_USER');

INSERT INTO APP_USER VALUES(10001, 'tester@qq.com', 'fufeng', 'magic', '$2a$10$SzBxM1XTgxzq7Wbivx.3M.QmDNscGkkLfYBWxHK76XCf8fhkBXP6e','ROLE_USER');

INSERT INTO APP_USERS_ROLES VALUES(10001, 10001);

