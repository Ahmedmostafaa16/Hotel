# Java Hotel OOP

Plain Java console project with in-memory collections. Requires JDK 11 or newer; verified with JDK 25. No console menu is included.

## Compile and run in Windows PowerShell

```powershell
Set-Location 'D:\Ahmed Mostafa\Java\Hotel'
New-Item -ItemType Directory -Force -Path out | Out-Null
$sources = @(Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
javac -Xlint:all -Werror -d out $sources
if ($LASTEXITCODE -ne 0) { throw 'Compilation failed' }
java -cp out hotel.Main
if ($LASTEXITCODE -ne 0) { throw 'Verification failed' }
```

Generated classes go into the ignored `out/` directory. Main prints each passed check and throws AssertionError on unexpected results; assertions do not require `-ea`.

## Class responsibilities

- `Hotel`: hotel details and room map; rejects duplicate rooms and exposes a read-only collection snapshot.
- `Room` / `RoomType`: room identity, category, and validated mutable nightly price.
- `Customer`: automatic final ID and required contact details.
- `StayPeriod`: immutable date range, night count, and overlap calculation; checkout is exclusive.
- `Reservation` / `ReservationStatus`: links a room, customer, and stay; captures the agreed total and enforces status transitions.
- `CustomerService`: registers customers and finds them by ID.
- `ReservationService`: holds one hotel's reservations, checks availability, books, finds reservations, returns customer snapshots, and performs cancellation/check-in/checkout by ID.
- `Main`: learner-oriented demonstration and explicit verification checks with three rooms and two registered customers.

Create a hotel, add rooms, register customers, then reuse one ReservationService across bookings. `reserve` returns true and immediately confirms a booking, or false for a conflicting active stay. Retrieve its generated ID through `getReservationsForCustomer`, then use `findReservationById` or the service operations. Missing lookup IDs return null; operations on missing IDs throw IllegalArgumentException.

Only CONFIRMED and CHECKED_IN reservations block overlapping dates for the same room. Adjacent stays and simultaneous stays in different rooms are allowed. Cancellation and checkout release dates. Status transitions are CONFIRMED to CHECKED_IN to CHECKED_OUT, or CONFIRMED to CANCELLED. Invalid transitions throw IllegalStateException.

Collection snapshots preserve membership, not deep copies of model objects. Prices and reservation statuses can still change through their validated methods. Data and generated IDs reset each process; services are intended for single-threaded console use. Totals use double for this learning project and remain fixed after booking.

Verification covers successful/overlapping/adjacent bookings, different rooms, availability, cancellation and checkout release, every invalid status transition, fixed totals, input validation, missing IDs, and collection snapshots.
