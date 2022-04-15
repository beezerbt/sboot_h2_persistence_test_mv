CREATE TABLE `billingorder` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `orderid` varchar(100) NOT NULL,
                                `ssid` varchar(100) DEFAULT NULL,
                                `action` varchar(100) NOT NULL,
                                `installation_date` datetime DEFAULT NULL,
                                `processid` varchar(100) DEFAULT NULL,
                                `status` varchar(100) DEFAULT NULL,
                                `order_request` text NOT NULL,
                                `order_response` text,
                                `subscription_id` bigint NOT NULL,
                                PRIMARY KEY (`id`));