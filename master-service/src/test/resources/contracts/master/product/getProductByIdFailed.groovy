package contracts.master.product

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return 404 for product id=2"

    request {
        url "/master-service/product/2"
        method GET()
        headers {
            header "X-Api-Version": "1"
            header "Accept-Language": "id-ID"
        }
    }

    response {
        status 404
        headers {
            contentType(applicationJson())
        }
        
        body (
                status: "NOT_FOUND",
                message: "Produk dengan id 2 tidak ada",
                errors: [
                    "Data tidak ada"
                ]
        )
    }

}
