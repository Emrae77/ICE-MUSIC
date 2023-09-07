
compose:
	mvn clean install
	docker-compose -f docker/docker-compose.yaml up -d icedb
	docker-compose -f docker/docker-compose.yaml build --no-cache
	docker-compose -f docker/docker-compose.yaml up ice_api

decompose:
	docker-compose -f docker/docker-compose.yaml down