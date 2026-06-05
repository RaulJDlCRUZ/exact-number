package com.kangoo.cyl.lambda;

import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kangoo.cyl.application.CifrasYLetrasService;
import com.kangoo.cyl.domain.entity.OptimalSolution;
import com.kangoo.cyl.domain.repository.SolutionRepository;
import com.kangoo.cyl.dto.SolveRequest;
import com.kangoo.cyl.dto.SolveResponse;
import com.kangoo.cyl.infrastructure.dynamodb.DynamoSolutionRepository;

public class SolverLambdaHandler implements RequestHandler<Map<String, Object>, SolveResponse> {

    private final ObjectMapper mapper = new ObjectMapper();

    private final SolutionRepository repo = new DynamoSolutionRepository();

    private final CifrasYLetrasService service = new CifrasYLetrasService(repo);

    @Override
    public SolveResponse handleRequest(Map<String, Object> input, Context context) {

        try {
            String body = (String) input.get("body");
            SolveRequest request = mapper.readValue(body, SolveRequest.class);

            OptimalSolution solution = service.solveA(
                    request.getNumbers(),
                    request.getTarget());

            SolveResponse response = new SolveResponse();
            response.target = request.getTarget();
            response.distance = solution.getMinimalDistance();
            response.operationsNeeded = solution.getOperationsNeeded();
            response.operations = solution.getOptimalSolution()
                    .stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());

            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}