Feature: Fidélité  Passer un bon d'achat Fidélité A déjà utilisé sur la caisse OpenBravo et voir son prix rester identique

  @TEST_NOE-3129
  Scenario Outline: Fidélité  Passer un bon d'achat Fidélité A déjà utilisé sur la caisse OpenBravo et voir son prix rester identique
    Given I have a ticket with product "<productCode>" in it
    When  I try to add the used voucher "<voucherCode>"
    Then A message should say the voucher is already used
    And The total should remains "<expectedTotal>"
    Examples:
      |productCode  |voucherCode|expectedTotal|
      | 2167322     |5555555555 |24.95      |





