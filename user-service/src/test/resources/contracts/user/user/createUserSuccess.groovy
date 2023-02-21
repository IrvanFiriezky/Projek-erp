package contracts.user.user

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "create user"

    request {
        url "/user-service/user"
        method POST()
        headers {
            contentType(applicationJson())
            header "X-Api-Version": "1"
            header "Accept-Language": "id-ID"
        }
        body (
            username: "user-test-new",
            password: "pass-test-new",
            firstName: "Test First",
            middleName: "Middle",
            lastName: "Last",
            email: "user-test-new@cranium.id",
            mobilePhone: "08111111111",
            domain: "test",
            status: 1
        )
    }

    response {
        status CREATED()
        headers {
            contentType(applicationJson())
        }
        body (
            id: 2,
            username: "user-test-new",
            firstName: "Test First",
            middleName: "Middle",
            lastName: "Last",
            email: "user-test-new@cranium.id",
            mobilePhone: "08111111111",
            domain: "test",
            status: 1,
            createdAt: "2023-01-30 15:30:59",
            createdBy: 1,
            updatedAt: "2023-01-30 15:30:59",
            updatedBy: 1,
            deleted: false
        )
    }

}
