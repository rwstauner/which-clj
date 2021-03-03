# net.r4s6.which

Find executable in PATH (or any file in a set of dirs).

## Usage

```clojure
; [net.r4s6/which "0.1.0"]

(require '[net.r4s6.which :as w])

(w/which "exe")
; "/usr/bin/exe"

(w/find-in ["dir1" "dir2"] "file" exists?)
; "dir2/file"

(w/which "nope")
; nil
```

## License

Copyright © 2021 Randy Stauner

Distributed under the Eclipse Public License version 1.0.
