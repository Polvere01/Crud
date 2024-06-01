provider "aws" {
  region = "sa-east-1" # Ajuste para a sua região
}

resource "aws_dynamodb_table" "users" {
  name           = "Users"
  billing_mode   = "PROVISIONED"
  read_capacity  = 5
  write_capacity = 5

  hash_key = "UserId"

  attribute {
    name = "UserId"
    type = "S"
  }

  tags = {
    Name = "UsersTable"
  }
}
