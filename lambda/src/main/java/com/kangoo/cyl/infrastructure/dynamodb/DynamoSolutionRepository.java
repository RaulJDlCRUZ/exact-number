package com.kangoo.cyl.infrastructure.dynamodb;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.kangoo.cyl.domain.entity.OptimalSolution;
import com.kangoo.cyl.domain.repository.SolutionRepository;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

public class DynamoSolutionRepository implements SolutionRepository {

    private final DynamoDbClient client = DynamoDbClient.create();

    private final String tableName = "CalculationResults";

    @Override
    public void save(Integer[] numbers, Integer target, OptimalSolution solution) {
        System.out.println("DYNAMO: save() called");

        Map<String, AttributeValue> item = new HashMap<>();

        String uuid = UUID.randomUUID().toString();
        System.out.printf("DYNAMO: saving: %s", uuid);

        item.put(
                "id",
                AttributeValue.fromS(
                        uuid));

        item.put(
                "createdAt",
                AttributeValue.fromS(
                        Instant.now().toString()));

        item.put(
                "numbers",
                AttributeValue.builder()
                        .l(
                                Arrays.stream(numbers)
                                        .map(n -> AttributeValue.fromN(
                                                n.toString()))
                                        .toList())
                        .build());

        item.put(
                "target",
                AttributeValue.fromN(
                        target.toString()));

        item.put(
                "distance",
                AttributeValue.fromN(
                        String.valueOf(
                                solution.getMinimalDistance())));

        item.put(
                "operationsNeeded",
                AttributeValue.fromN(
                        String.valueOf(
                                solution.getOperationsNeeded())));

        item.put(
                "bestResult",
                AttributeValue.fromN(
                        String.valueOf(
                                solution.getBestResult())));

        item.put(
                "operations",
                AttributeValue.builder()
                        .l(
                                solution.getOptimalSolution()
                                        .stream()
                                        .map(op -> AttributeValue.fromS(
                                                op.toString()))
                                        .toList())
                        .build());

        client.putItem(
                PutItemRequest.builder()
                        .tableName(tableName)
                        .item(item)
                        .build());

        System.out.println("DYNAMO: save() completed");
    }
}