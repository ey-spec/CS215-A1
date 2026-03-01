import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] types = new String[100];
        String[] categories = new String[100];
        double[] amounts = new double[100];

        int count = 0;
        int choice;
        boolean reportShown = false;

        do {
            System.out.println("\n=== PERSONAL FINANCE TRACKER ===");
            System.out.println("1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. Show Report (will exit after report)");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                if (reportShown) {
                    System.out.println("Report has already been shown. Program will now exit.");
                    break;
                }
                types[count] = "Income";
                System.out.print("Enter category (e.g., Salary, Freelance, etc.): ");
                categories[count] = scanner.nextLine();
                System.out.print("Enter amount: $");
                amounts[count] = scanner.nextDouble();
                System.out.println("Income added successfully!");
                count++;
            }

            else if (choice == 2) {
                if (reportShown) {
                    System.out.println("Report has already been shown. Program will now exit.");
                    break;
                }
                types[count] = "Expense";
                System.out.print("Enter category (e.g., Food, Rent, Transport, etc.): ");
                categories[count] = scanner.nextLine();
                System.out.print("Enter amount: $");
                amounts[count] = scanner.nextDouble();
                System.out.println("Expense added successfully!");
                count++;
            }

            else if (choice == 3) {
                double balance = 0;
                double totalIncome = 0;
                double totalExpenses = 0;

                System.out.println("\n=== MONTHLY FINANCIAL REPORT ===");
                System.out.println("=================================");

                System.out.println("\n--- TRANSACTION HISTORY ---");
                System.out.printf("%-10s | %-15s | %s%n", "Type", "Category", "Amount");
                System.out.println("----------------------------------------");

                for (int i = 0; i < count; i++) {
                    System.out.printf("%-10s | %-15s | $%.2f%n",
                        types[i], categories[i], amounts[i]);

                    if (types[i].equals("Income")) {
                        balance += amounts[i];
                        totalIncome += amounts[i];
                    }
                    else {
                        balance -= amounts[i];
                        totalExpenses += amounts[i];
                    }
                }

                System.out.println("----------------------------------------");
                System.out.printf("TOTAL INCOME: $%.2f%n", totalIncome);
                System.out.printf("TOTAL EXPENSES: $%.2f%n", totalExpenses);
                System.out.printf("CURRENT BALANCE: $%.2f%n", balance);

                double avgExpensesPerDay = totalExpenses / 30;
                System.out.println("\n--- AVERAGE DAILY EXPENSES ---");
                System.out.printf("Average per day: $%.2f%n", avgExpensesPerDay);

                System.out.println("\n=== 80% SPENDING RATIO ANALYSIS ===");

                if (totalIncome > 0) {
                    double eightyPercentOfIncome = totalIncome * 0.80;
                    double twentyPercentSavings = totalIncome * 0.20;

                    System.out.printf("Your total income: $%.2f%n", totalIncome);
                    System.out.printf("80%% for expenses (recommended): $%.2f%n", eightyPercentOfIncome);
                    System.out.printf("20%% for savings (recommended): $%.2f%n", twentyPercentSavings);
                    System.out.println("\n--- WHAT YOU SPENT ---");
                    System.out.printf("Actual expenses: $%.2f%n", totalExpenses);

                    double expenseRatio = (totalExpenses / eightyPercentOfIncome) * 100;

                    if (totalExpenses <= eightyPercentOfIncome) {
                        System.out.println("✅ GOOD JOB! You're within the 80% spending limit.");
                        System.out.printf("You spent %.1f%% of your recommended spending limit.%n", expenseRatio);
                        System.out.printf("Under budget by: $%.2f%n", (eightyPercentOfIncome - totalExpenses));

                        double recommendedDaily = eightyPercentOfIncome / 30;
                        if (avgExpensesPerDay <= recommendedDaily) {
                            System.out.printf("✓ Your daily average ($%.2f) is within the recommended daily limit ($%.2f)%n",
                                avgExpensesPerDay, recommendedDaily);
                        }

                    }
                    else {
                        System.out.println("⚠️  WARNING! You've exceeded the 80% spending limit!");
                        System.out.printf("You spent %.1f%% of your recommended spending limit.%n", expenseRatio);
                        System.out.printf("Overspent by: $%.2f%n", (totalExpenses - eightyPercentOfIncome));

                        double recommendedDaily = eightyPercentOfIncome / 30;
                        System.out.printf("Your daily average: $%.2f%n", avgExpensesPerDay);
                        System.out.printf("Recommended daily max: $%.2f%n", recommendedDaily);
                        System.out.printf("You're overspending by $%.2f per day on average!%n",
                            (avgExpensesPerDay - recommendedDaily));
                    }

                    System.out.println("\n--- RECOMMENDATIONS ---");
                    if (totalExpenses > eightyPercentOfIncome) {
                        double amountToReduce = totalExpenses - eightyPercentOfIncome;
                        double reducePerDay = amountToReduce / 30;
                        System.out.println("To reach 80% ratio, you need to reduce expenses by:");
                        System.out.printf("• Total: $%.2f%n", amountToReduce);
                        System.out.printf("• Per day: $%.2f%n", reducePerDay);
                    }
                    else {
                        double extraForSavings = eightyPercentOfIncome - totalExpenses;
                        System.out.printf("You can add $%.2f to your savings (20%% target: $%.2f)%n",
                            extraForSavings, twentyPercentSavings);
                        System.out.printf("Your current savings rate: %.1f%%%n", (balance / totalIncome) * 100);
                    }

                }
                else {
                    System.out.println("No income recorded. Add income first to see ratio analysis.");
                }

                System.out.println("\n=================================");
                System.out.println("Report complete. Program will now exit.");

                reportShown = true;

                System.out.println("\nPress Enter to exit...");
                scanner.nextLine();
                break;
            }

        } while (choice != 4 && !reportShown);

        System.out.println("\nThank you for using Personal Finance Tracker. Goodbye!");
        scanner.close();
    }
}