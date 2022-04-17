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
CREATE TABLE `customer` (
                          id bigint(20) NOT NULL AUTO_INCREMENT,
                          cdbid varchar(20) NOT NULL,
                          description varchar(10000) NOT NULL,
                          email varchar(100) NOT NULL,
                          is_active tinyint(1) NOT NULL,
                          name varchar(512) NOT NULL,
                          trial_end date NOT NULL,
                          PRIMARY KEY (id));
