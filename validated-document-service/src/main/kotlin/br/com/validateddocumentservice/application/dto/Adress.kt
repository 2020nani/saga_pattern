package br.com.validateddocumentservice.application.dto

class Adress {
    private val id: Int? = null
    private val code: String? = null
    private val street: String? = null
    private val number: String? = null
    private val district: String? = null

    fun getId() : Int? {return this.id}
    fun getCode(): String? {return this.code};
    fun getStreet(): String? {return this.street};
    fun getNumber(): String? {return this.number};
    fun getDistrict(): String? {return this.district};

    override fun toString(): String {
        return "Adress(id=$id, code=$code, street=$street, number=$number, district=$district)"
    }
}
