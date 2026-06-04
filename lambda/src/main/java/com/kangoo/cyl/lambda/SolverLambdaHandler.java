package com.kangoo.cyl.lambda;

import com.kangoo.cyl.application.CifrasYLetrasService;
import com.kangoo.cyl.domain.entity.OptimalSolution;
import com.kangoo.cyl.domain.repository.SolutionRepository;
import com.kangoo.cyl.dto.SolveRequest;
import com.kangoo.cyl.dto.SolveResponse;
import com.kangoo.cyl.infrastructure.dynamodb.DynamoSolutionRepository;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.Context;
import java.util.stream.Collectors;

public class SolverLambdaHandler implements RequestHandler<SolveRequest, SolveResponse> {

    SolutionRepository repo = new DynamoSolutionRepository(); // ELIJO IN MEMORY PERO SI SE QUIERE USAR DYNAMO CAMBIAR
    private final CifrasYLetrasService service = new CifrasYLetrasService(repo);

    @Override
    public SolveResponse handleRequest(SolveRequest request, Context context) {
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
    }
}