package contracts.user.scope

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return userScopeHasDto by scope name = USER_SCOPE_OWN"

    request {
        url "/user-service/scope/me/USER_SCOPE_OWN"
        method GET()
        headers {
            header "X-Api-Version": "1"
            header "Accept-Language": "id-ID"
        }
    }

    response {
        status OK()
        headers {
            contentType(applicationJson())
        }
        body (
                scopeName: "USER_SCOPE_OWN",
                scopeValue: "12",
        )
    }

}
