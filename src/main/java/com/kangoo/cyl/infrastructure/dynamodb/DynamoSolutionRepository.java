package com.kangoo.cyl.infrastructure.dynamodb;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import com.kangoo.cyl.domain.repository.SolutionRepository;

import com.kangoo.cyl.domain.entity.OptimalSolution;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DynamoSolutionRepository implements SolutionRepository {

    private final DynamoDbClient client = DynamoDbClient.create();

    private final String tableName = "CalculationResults";

    @Override
    public void save(OptimalSolution solution) {

        Map<String, AttributeValue> item = new HashMap<>();

        item.put("id", AttributeValue.fromS(UUID.randomUUID().toString()));
        item.put("distance", AttributeValue.fromN(String.valueOf(solution.getMinimalDistance())));
        item.put("operationsNeeded", AttributeValue.fromN(String.valueOf(solution.getOperationsNeeded())));

        PutItemRequest request = PutItemRequest.builder()
                .tableName(tableName)
                .item(item)
                .build();

        client.putItem(request);
    }
}