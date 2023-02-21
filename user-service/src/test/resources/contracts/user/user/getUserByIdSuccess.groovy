package contracts.user.user

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return user by id=1"

    request {
        url "/user-service/user/12"
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
                id: 12,
                username: "user-test",
                firstName: "Test firstname",
                middleName: "Test middlename",
                lastName: "Test lastname",
                email: "user-test@cranium.id",
                mobilePhone: "08311111111",
                domain: "user",
                status: 1,
                createdAt: "2023-01-30 15:30:59",
                createdBy: 1,
                updatedAt: "2023-01-30 15:30:59",
                updatedBy: 1,
                deleted: false
        )
    }

}
