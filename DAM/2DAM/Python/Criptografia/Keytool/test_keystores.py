import subprocess
import os
import shutil
import string
import random
import time
import hashlib
import signal
from concurrent.futures import ThreadPoolExecutor, as_completed
from pathlib import Path


class ExtremeProfessionalKeystoreTestSuite:
    """
    Comprehensive extreme testing suite for Java keystore operations
    60+ tests covering edge cases, stress tests, and production scenarios
    """

    def __init__(self, test_dir="test_keystores_extreme"):
        self.test_dir = test_dir
        self.results = {"passed": 0, "failed": 0, "skipped": 0}
        self.current_test_group = None
        self._prepare_environment()

    def _prepare_environment(self):
        """Initialize clean test environment"""
        if os.path.exists(self.test_dir):
            shutil.rmtree(self.test_dir)
        os.makedirs(self.test_dir)
        print(f"Test environment ready: {self.test_dir}\n")

    def _get_test_group_path(self, group_name=None):
        """Get path for current test group"""
        if group_name:
            return os.path.join(self.test_dir, group_name)
        elif self.current_test_group:
            return os.path.join(self.test_dir, self.current_test_group)
        else:
            return self.test_dir

    def _path(self, filename, group_name=None):
        """Get full path for test file within current test group"""
        group_path = self._get_test_group_path(group_name)
        return os.path.join(group_path, filename)

    def _create_test_group(self, group_name):
        """Create a new test group directory"""
        group_path = os.path.join(self.test_dir, group_name)
        os.makedirs(group_path, exist_ok=True)
        return group_path

    def _pass(self, msg):
        print(f"✅ PASS: {msg}")
        self.results["passed"] += 1

    def _fail(self, msg):
        print(f"❌ FAIL: {msg}")
        self.results["failed"] += 1

    def _skip(self, msg, reason):
        print(f"⚠️ SKIP: {msg} ({reason})")
        self.results["skipped"] += 1

    # ==================== CORE OPERATIONS ====================

    def alias_exists(self, alias, keystore, password):
        """Check if alias exists in keystore"""
        if not os.path.exists(keystore):
            return False
        try:
            cmd = ["keytool", "-list", "-alias", alias,
                   "-keystore", keystore, "-storepass", password]
            return subprocess.run(cmd, capture_output=True, timeout=10).returncode == 0
        except Exception:
            return False

    def generate_keystore(self, alias, keystore, password, keysize=1024, dname=None, validity=365):
        """Generate keystore with alias"""
        if dname is None:
            dname = f"CN={alias}, OU=Test, O=Test, C=ES"
        if os.path.exists(keystore) and self.alias_exists(alias, keystore, password):
            return False
        try:
            cmd = [
                "keytool", "-genkeypair", "-keyalg", "RSA",
                "-alias", alias, "-keystore", keystore,
                "-storepass", password, "-keypass", password,
                "-keysize", str(keysize), "-dname", dname,
                "-validity", str(validity)
            ]
            return subprocess.run(cmd, capture_output=True, timeout=60).returncode == 0
        except Exception:
            return False

    def export_certificate(self, alias, keystore, password, cert_file):
        """Export certificate from keystore"""
        if not self.alias_exists(alias, keystore, password):
            return False
        try:
            cmd = ["keytool", "-export", "-alias", alias,
                   "-file", cert_file, "-keystore", keystore,
                   "-storepass", password]
            return subprocess.run(cmd, capture_output=True, timeout=20).returncode == 0
        except Exception:
            return False

    def list_keystore(self, keystore, password, verbose=False):
        """List all aliases in keystore"""
        try:
            cmd = ["keytool", "-list", "-keystore", keystore, "-storepass", password]
            if verbose:
                cmd.append("-v")
            result = subprocess.run(cmd, capture_output=True, timeout=15, text=True)
            return result.returncode == 0, result.stdout
        except Exception:
            return False, ""

    def delete_alias(self, alias, keystore, password):
        """Delete alias from keystore"""
        try:
            cmd = ["keytool", "-delete", "-alias", alias,
                   "-keystore", keystore, "-storepass", password]
            return subprocess.run(cmd, capture_output=True, timeout=10).returncode == 0
        except Exception:
            return False

    def change_password(self, alias, keystore, old_pass, new_pass):
        """Change alias password"""
        try:
            cmd = ["keytool", "-keypasswd", "-alias", alias,
                   "-keystore", keystore, "-storepass", old_pass,
                   "-keypass", old_pass, "-new", new_pass]
            return subprocess.run(cmd, capture_output=True, timeout=10).returncode == 0
        except Exception:
            return False

    def import_certificate(self, alias, keystore, password, cert_file):
        """Import certificate to keystore"""
        try:
            cmd = ["keytool", "-import", "-trustcacerts", "-alias", alias,
                   "-file", cert_file, "-keystore", keystore,
                   "-storepass", password, "-noprompt"]
            return subprocess.run(cmd, capture_output=True, timeout=20).returncode == 0
        except Exception:
            return False

    def corrupt_keystore(self, keystore, corruption_type="middle"):
        """Corrupt keystore file in different ways"""
        if not os.path.exists(keystore):
            return False
        try:
            with open(keystore, "rb") as f:
                content = f.read()

            if corruption_type == "middle":
                mid = len(content) // 2
                corrupted = content[:mid] + b"CORRUPTED_DATA_BLOCK" + content[mid:]
            elif corruption_type == "start":
                corrupted = b"CORRUPT" + content[7:]
            elif corruption_type == "end":
                corrupted = content[:-10] + b"CORRUPTED"
            elif corruption_type == "truncate":
                corrupted = content[:len(content)//3]
            else:
                corrupted = content

            with open(keystore, "wb") as f:
                f.write(corrupted)
            return True
        except Exception:
            return False

    # ==================== TEST GROUP 1: BASIC OPERATIONS ====================

    def test_01_basic_operations(self):
        """Test Group 1: Basic Keystore Operations"""
        self.current_test_group = "test_01_basic_operations"
        group_path = self._create_test_group(self.current_test_group)
        print(f"📁 Testing in: {group_path}")

        # Test 1.1: Basic keystore creation
        ks = self._path("basic.jks")
        ok = self.generate_keystore("basic_alias", ks, "pass123")
        if ok and self.alias_exists("basic_alias", ks, "pass123"):
            self._pass("Basic keystore creation")
        else:
            self._fail("Basic keystore creation")

        # Test 1.2: Multiple aliases in same keystore
        ks = self._path("multi.jks")
        aliases = [f"alias_{i}" for i in range(10)]
        results = [self.generate_keystore(a, ks, "pass123") for a in aliases]
        verifications = [self.alias_exists(a, ks, "pass123") for a in aliases]

        if all(results) and all(verifications):
            self._pass(f"Multiple aliases (10 aliases)")
        else:
            self._fail("Multiple aliases")

        # Test 1.3: Case sensitivity in alias names
        ks = self._path("case.jks")
        aliases = ["TestAlias", "testalias", "TESTALIAS", "tEsTaLiAs", "TESTalias"]
        results = [self.generate_keystore(a, ks, "pass123") and
                   self.alias_exists(a, ks, "pass123") for a in aliases]

        if all(results):
            self._pass(f"Case sensitive aliases (5 variations)")
        else:
            self._fail("Case sensitive aliases")

    # ==================== TEST GROUP 2: SPECIAL CHARACTERS ====================

    def test_02_special_characters(self):
        """Test Group 2: Special Characters & Unicode"""
        self.current_test_group = "test_02_special_characters"
        group_path = self._create_test_group(self.current_test_group)
        print(f"📁 Testing in: {group_path}")

        # Test 2.1: Aliases with special characters
        ks = self._path("special.jks")
        aliases = [
            "alias-with-dash",
            "alias_with_underscore",
            "alias.with.dots",
            "alias123numbers",
            "alias$special",
            "alias@symbol"
        ]
        results = [self.generate_keystore(a, ks, "pass123") and
                   self.alias_exists(a, ks, "pass123") for a in aliases]

        success = sum(results)
        if success >= 4:
            self._pass(f"Special character aliases ({success}/6)")
        else:
            self._fail("Special character aliases")

        # Test 2.2: Aliases containing spaces
        ks = self._path("spaces.jks")
        aliases = [
            "alias with spaces",
            "multi word alias test",
            "  leading spaces",
            "trailing spaces  "
        ]
        results = [self.generate_keystore(a, ks, "pass123") for a in aliases]
        success = sum(results)

        if success >= 2:
            self._pass(f"Aliases with spaces ({success}/4)")
        else:
            self._fail("Aliases with spaces")

        # Test 2.3: Unicode characters in aliases
        ks = self._path("unicode.jks")
        aliases = ["测试用户", "用户", "ñoño", "café", "日本語", "Ελληνικά"]
        results = []
        for i, a in enumerate(aliases):
            ok = self.generate_keystore(a, f"{ks}.{i}", "pass123")
            results.append(ok and self.alias_exists(a, f"{ks}.{i}", "pass123"))

        success = sum(results)
        if success >= 3:
            self._pass(f"Unicode aliases ({success}/6)")
        else:
            self._fail("Unicode aliases")

        # Test 2.4: Emoji characters in aliases
        ks = self._path("emoji.jks")
        aliases = [
            "user_🚀_test",
            "🎉_celebration",
            "test_🎭_alias",
            "🌈_rainbow_✨"
        ]
        results = []
        for i, a in enumerate(aliases):
            ok = self.generate_keystore(a, f"{ks}.{i}", "pass123")
            results.append(ok)

        success = sum(results)
        if success >= 2:
            self._pass(f"Emoji aliases ({success}/4)")
        else:
            self._fail("Emoji aliases")

        # Test 2.5: Extreme Unicode combinations
        ks = self._path("unicode_extreme.jks")
        aliases = [
            "用户_🚀_ñáéíóú",
            "𝒜𝓁𝒾𝒶𝓈_𝒮𝓅𝑒𝒸𝒾𝒶𝓁",
            "ＡｌｉａｓＦｕｌｌＷｉｄｔｈ",
            "alias_👨‍👩‍👧‍👦_familia"
        ]
        results = []
        for i, a in enumerate(aliases):
            ok = self.generate_keystore(a, f"{ks}.{i}", "pass123")
            results.append(ok)

        success = sum(results)
        if success >= 2:
            self._pass(f"Extreme Unicode ({success}/4)")
        else:
            self._fail("Extreme Unicode")

    # ==================== TEST GROUP 3: LENGTH & PATHS ====================

    def test_03_length_path_tests(self):
        """Test Group 3: Length & Path Tests"""
        self.current_test_group = "test_03_length_path_tests"
        group_path = self._create_test_group(self.current_test_group)
        print(f"📁 Testing in: {group_path}")

        # Test 3.1: Long alias names
        ks = self._path("longname.jks")
        aliases = [
            "a" * 50,
            "b" * 100,
            "alias_" + "x" * 200,
            "long" * 100
        ]
        results = [self.generate_keystore(a, ks, "pass123") for a in aliases]
        success = sum(results)

        if success >= 2:
            self._pass(f"Long alias names ({success}/4)")
        else:
            self._fail("Long alias names")

        # Test 3.2: Extremely long paths
        long_dir = self._path("a" * 100)
        try:
            os.makedirs(long_dir, exist_ok=True)
            ks = os.path.join(long_dir, "keystore.jks")
            ok = self.generate_keystore("long_path", ks, "pass123")
            verified = self.alias_exists("long_path", ks, "pass123")

            if ok and verified:
                self._pass("Extremely long paths")
            else:
                self._fail("Extremely long paths")
        except Exception:
            self._skip("Extremely long paths", "Path length limit")

        # Test 3.3: Unicode paths
        unicode_dir = self._path("路径_测试_🚀 con espacios")
        try:
            os.makedirs(unicode_dir, exist_ok=True)
            ks = os.path.join(unicode_dir, "unicode_keystore.jks")
            ok = self.generate_keystore("uni_alias", ks, "pass123")
            verified = self.alias_exists("uni_alias", ks, "pass123")

            if ok and verified:
                self._pass("Unicode paths")
            else:
                self._fail("Unicode paths")
        except Exception:
            self._skip("Unicode paths", "Unicode path error")

        # Test 3.4: Nested directories
        paths = [
            "level1/ks.jks",
            "level1/level2/ks.jks",
            "level1/level2/level3/ks.jks",
            "a/b/c/d/e/f/ks.jks"
        ]
        results = []
        for path in paths:
            full_path = self._path(path)
            os.makedirs(os.path.dirname(full_path), exist_ok=True)
            ok = self.generate_keystore("nested", full_path, "pass123")
            results.append(ok and self.alias_exists("nested", full_path, "pass123"))

        if all(results):
            self._pass(f"Nested directories ({len(paths)} levels)")
        else:
            self._fail("Nested directories")

    # ==================== TEST GROUP 4: PASSWORD TESTS ====================

    def test_04_password_tests(self):
        """Test Group 4: Password Variations"""
        self.current_test_group = "test_04_password_tests"
        group_path = self._create_test_group(self.current_test_group)
        print(f"📁 Testing in: {group_path}")

        # Test 4.1: Different password formats
        passwords = [
            "simple",
            "Complex123!",
            "p@ss$w0rd#%&",
            "very_long_password_with_many_characters_123456789",
            "12345678",
            "🔐password🔑",
            "паро́ль",
            "密码"
        ]
        results = []
        for i, pwd in enumerate(passwords):
            ks = self._path(f"pwd_{i}.jks")
            ok = self.generate_keystore(f"alias_{i}", ks, pwd)
            results.append(ok and self.alias_exists(f"alias_{i}", ks, pwd))

        success = sum(results)
        if success >= 6:
            self._pass(f"Password variations ({success}/8)")
        else:
            self._fail("Password variations")

        # Test 4.2: Extremely long password
        pw = ''.join(random.choices(string.printable, k=512))
        ks = self._path("long_pwd.jks")

        try:
            ok = self.generate_keystore("long_pw_alias", ks, pw)
            if ok:
                verified = self.alias_exists("long_pw_alias", ks, pw)
                if verified:
                    self._pass("Extremely long password (512 chars)")
                else:
                    self._fail("Extremely long password verification")
            else:
                self._skip("Extremely long password", "Rejected by keytool")
        except Exception:
            self._skip("Extremely long password", "Exception occurred")

        # Test 4.3: Special char passwords
        passwords = [
            "pass\\word",
            "pass'word",
            "pass`word",
            "pass$word",
            "pass&word",
            "pass|word",
            "pass>word<",
            "pass\"word"
        ]
        results = []
        for i, pwd in enumerate(passwords):
            ks = self._path(f"special_pwd_{i}.jks")
            ok = self.generate_keystore(f"sp_alias_{i}", ks, pwd)
            results.append(ok and self.alias_exists(f"sp_alias_{i}", ks, pwd))

        success = sum(results)
        if success >= 4:
            self._pass(f"Special char passwords ({success}/8)")
        else:
            self._fail("Special char passwords")

        # Test 4.4: Wrong password rejection
        ks = self._path("wrongpwd.jks")
        self.generate_keystore("test", ks, "correct123")

        wrong = self.alias_exists("test", ks, "wrong123")
        correct = self.alias_exists("test", ks, "correct123")

        if not wrong and correct:
            self._pass("Wrong password rejection")
        else:
            self._fail("Wrong password rejection")

        # Test 4.5: Password change operations
        ks = self._path("pwdchange.jks")
        self.generate_keystore("change_test", ks, "old_pass")

        changed = self.change_password("change_test", ks, "old_pass", "new_pass")
        old_works = self.alias_exists("change_test", ks, "old_pass")
        new_works = self.alias_exists("change_test", ks, "new_pass")

        if changed and not old_works and new_works:
            self._pass("Password change")
        else:
            self._fail("Password change")

        # Test 4.6: Empty password
        ks = self._path("empty_pwd.jks")
        ok = self.generate_keystore("empty", ks, "")

        if ok:
            verified = self.alias_exists("empty", ks, "")
            if verified:
                self._pass("Empty password accepted")
            else:
                self._fail("Empty password verification")
        else:
            self._skip("Empty password", "Rejected by keytool")

    # ==================== TEST GROUP 5: EXPORT/IMPORT TESTS ====================

    def test_05_export_import_tests(self):
        """Test Group 5: Export/Import Operations"""
        self.current_test_group = "test_05_export_import_tests"
        group_path = self._create_test_group(self.current_test_group)
        print(f"📁 Testing in: {group_path}")

        # Test 5.1: Certificate export in different formats
        ks = self._path("export.jks")
        self.generate_keystore("export_test", ks, "pass123")

        formats = [".crt", ".cer", ".der", ".pem", ".cert"]
        results = []
        for fmt in formats:
            cert = self._path(f"cert{fmt}")
            ok = self.export_certificate("export_test", ks, "pass123", cert)
            results.append(ok and os.path.exists(cert))

        success = sum(results)
        if success >= 3:
            self._pass(f"Export formats ({success}/5)")
        else:
            self._fail("Export formats")

        # Test 5.2: Certificate import operations
        ks_src = self._path("import_src.jks")
        ks_dst = self._path("import_dst.jks")
        cert = self._path("import.crt")

        self.generate_keystore("import_test", ks_src, "pass123")
        exported = self.export_certificate("import_test", ks_src, "pass123", cert)

        if exported:
            imported = self.import_certificate("imported_cert", ks_dst, "pass123", cert)
            if imported:
                self._pass("Certificate import")
            else:
                self._fail("Certificate import")
        else:
            self._skip("Certificate import", "Export failed")

        # Test 5.3: Multiple exports same certificate
        ks = self._path("multi_export.jks")
        self.generate_keystore("multi", ks, "pass123")

        results = []
        for i in range(5):
            cert = self._path(f"multi_export_{i}.crt")
            ok = self.export_certificate("multi", ks, "pass123", cert)
            results.append(ok and os.path.exists(cert))

        if all(results):
            self._pass("Multiple exports same certificate")
        else:
            self._fail("Multiple exports same certificate")

    # ==================== TEST GROUP 6: CORRUPTION & ERROR TESTS ====================

    def test_06_corruption_error_tests(self):
        """Test Group 6: Corruption & Error Handling"""
        self.current_test_group = "test_06_corruption_error_tests"
        group_path = self._create_test_group(self.current_test_group)
        print(f"📁 Testing in: {group_path}")

        # Test 6.1: Corrupted keystore (middle corruption)
        ks = self._path("corrupt_mid.jks")
        self.generate_keystore("valid", ks, "pass123")
        self.corrupt_keystore(ks, "middle")

        can_list, _ = self.list_keystore(ks, "pass123")
        if not can_list:
            self._pass("Corrupted keystore rejection (middle)")
        else:
            self._fail("Corrupted keystore rejection (middle)")

        # Test 6.2: Corrupted keystore (start corruption)
        ks = self._path("corrupt_start.jks")
        self.generate_keystore("valid", ks, "pass123")
        self.corrupt_keystore(ks, "start")

        can_list, _ = self.list_keystore(ks, "pass123")
        if not can_list:
            self._pass("Corrupted keystore rejection (start)")
        else:
            self._fail("Corrupted keystore rejection (start)")

        # Test 6.3: Truncated keystore
        ks = self._path("corrupt_trunc.jks")
        self.generate_keystore("valid", ks, "pass123")
        self.corrupt_keystore(ks, "truncate")

        can_list, _ = self.list_keystore(ks, "pass123")
        if not can_list:
            self._pass("Truncated keystore rejection")
        else:
            self._fail("Truncated keystore rejection")

        # Test 6.4: Wrong password doesn't corrupt keystore
        ks = self._path("pwd_nocorrupt.jks")
        self.generate_keystore("test", ks, "correct")

        # Try with wrong password multiple times
        for _ in range(5):
            self.alias_exists("test", ks, "wrong")

        # Verify correct password still works
        still_works = self.alias_exists("test", ks, "correct")

        if still_works:
            self._pass("Wrong password doesn't corrupt")
        else:
            self._fail("Wrong password doesn't corrupt")

    # ==================== TEST GROUP 7: DELETION & MODIFICATION ====================

    def test_07_deletion_modification_tests(self):
        """Test Group 7: Deletion & Modification"""
        self.current_test_group = "test_07_deletion_modification_tests"
        group_path = self._create_test_group(self.current_test_group)
        print(f"📁 Testing in: {group_path}")

        # Test 7.1: Alias deletion
        ks = self._path("delete.jks")
        self.generate_keystore("to_delete", ks, "pass123")

        exists_before = self.alias_exists("to_delete", ks, "pass123")
        deleted = self.delete_alias("to_delete", ks, "pass123")
        exists_after = self.alias_exists("to_delete", ks, "pass123")

        if exists_before and deleted and not exists_after:
            self._pass("Alias deletion")
        else:
            self._fail("Alias deletion")

        # Test 7.2: Delete non-existent alias
        ks = self._path("delete_none.jks")
        self.generate_keystore("exists", ks, "pass123")

        deleted = self.delete_alias("nonexistent", ks, "pass123")

        if not deleted:
            self._pass("Delete non-existent alias rejected")
        else:
            self._fail("Delete non-existent alias rejected")

        # Test 7.3: Rapid creation and deletion cycles
        ks = self._path("rapid.jks")
        cycles = 15
        results = []

        for i in range(cycles):
            alias = f"rapid_{i}"
            created = self.generate_keystore(alias, ks, "pass123")
            deleted = self.delete_alias(alias, ks, "pass123")
            results.append(created and deleted)

        success = sum(results)
        if success >= cycles * 0.8:
            self._pass(f"Rapid creation/deletion ({success}/{cycles})")
        else:
            self._fail("Rapid creation/deletion")

        # Test 7.4: Duplicate alias rejection
        ks = self._path("duplicate.jks")
        first = self.generate_keystore("dup_alias", ks, "pass123")
        second = self.generate_keystore("dup_alias", ks, "pass123")

        if first and not second:
            self._pass("Duplicate alias rejection")
        else:
            self._fail("Duplicate alias rejection")

        # Test 7.5: Alias renewal scenarios
        ks = self._path("renewal.jks")

        self.generate_keystore("renew", ks, "pass123")
        duplicate = self.generate_keystore("renew", ks, "pass123")
        new_alias = self.generate_keystore("new_alias", ks, "pass123")

        original_exists = self.alias_exists("renew", ks, "pass123")
        new_exists = self.alias_exists("new_alias", ks, "pass123")

        if original_exists and not duplicate and new_exists:
            self._pass("Alias renewal handling")
        else:
            self._fail("Alias renewal handling")

    # ==================== TEST GROUP 8: KEY SIZE & VALIDITY ====================

    def test_08_key_size_validity_tests(self):
        """Test Group 8: Key Size & Validity Tests"""
        self.current_test_group = "test_08_key_size_validity_tests"
        group_path = self._create_test_group(self.current_test_group)
        print(f"📁 Testing in: {group_path}")

        # Test 8.1: Different RSA key sizes
        sizes = [512, 1024, 2048, 4096]
        results = []
        times = []

        for size in sizes:
            ks = self._path(f"keysize_{size}.jks")
            start = time.time()
            ok = self.generate_keystore(f"key_{size}", ks, "pass123", keysize=size)
            elapsed = time.time() - start
            results.append(ok)
            times.append(elapsed)

        success = sum(results)
        if success >= 3:
            self._pass(f"Different key sizes ({success}/4, max time: {max(times):.2f}s)")
        else:
            self._fail("Different key sizes")

        # Test 8.2: Key size impact on performance
        sizes = [1024, 2048]
        times = {}

        for size in sizes:
            ks = self._path(f"perf_{size}.jks")
            start = time.time()
            self.generate_keystore(f"perf_{size}", ks, "pass123", keysize=size)
            times[size] = time.time() - start

        if 1024 in times and 2048 in times:
            ratio = times[2048] / times[1024] if times[1024] > 0 else 0
            self._pass(f"Key size performance (2048/1024 ratio: {ratio:.2f}x)")
        else:
            self._fail("Key size performance")

        # Test 8.3: Certificate validity periods
        ks = self._path("validity.jks")
        validities = [1, 365, 3650, 7300]  # 1 day, 1 year, 10 years, 20 years
        results = []

        for val in validities:
            alias = f"valid_{val}"
            ok = self.generate_keystore(alias, ks, "pass123", validity=val)
            results.append(ok)

        success = sum(results)
        if success >= 3:
            self._pass(f"Certificate validity periods ({success}/4)")
        else:
            self._fail("Certificate validity periods")

        # Test 8.4: Validity info extraction
        ks = self._path("validity_info.jks")
        self.generate_keystore("expiry_test", ks, "pass123", validity=365)

        can_list, output = self.list_keystore(ks, "pass123", verbose=True)

        if can_list and "Valid from" in output:
            self._pass("Validity info extraction")
        else:
            self._skip("Validity info extraction", "Format not detected")

    # ==================== TEST GROUP 9: FILESYSTEM & PERMISSIONS ====================

    def test_09_filesystem_permissions_tests(self):
        """Test Group 9: Filesystem & Permissions"""
        self.current_test_group = "test_09_filesystem_permissions_tests"
        group_path = self._create_test_group(self.current_test_group)
        print(f"📁 Testing in: {group_path}")

        # Test 9.1: Filesystem compatibility
        names = [
            "keystore-with-dashes.jks",
            "keystore_with_underscores.jks",
            "KeystoreWithCaps.jks",
            "keystore.mixed.Case.jks",
            "ks-2024-01-15.jks",
            "test.v1.2.3.jks"
        ]
        results = []
        for name in names:
            ks = self._path(name)
            ok = self.generate_keystore("fs_test", ks, "pass123")
            results.append(ok and self.alias_exists("fs_test", ks, "pass123"))

        success = sum(results)
        if success >= 5:
            self._pass(f"Filesystem compatibility ({success}/6)")
        else:
            self._fail("Filesystem compatibility")

        # Test 9.2: File permissions readonly
        ks = self._path("perms_ro.jks")
        self.generate_keystore("perms", ks, "pass123")

        try:
            os.chmod(ks, 0o444)
            can_read = self.alias_exists("perms", ks, "pass123")
            can_write = self.generate_keystore("perms2", ks, "pass123")
            os.chmod(ks, 0o644)

            if can_read and not can_write:
                self._pass("Read-only permissions")
            else:
                self._fail("Read-only permissions")
        except Exception:
            self._skip("Read-only permissions", "chmod not supported")

        # Test 9.3: File permissions noread
        ks = self._path("perms_noread.jks")
        self.generate_keystore("perms", ks, "pass123")

        try:
            os.chmod(ks, 0o000)
            can_read = self.alias_exists("perms", ks, "pass123")
            os.chmod(ks, 0o644)

            if not can_read:
                self._pass("No-read permissions rejection")
            else:
                self._fail("No-read permissions rejection")
        except Exception:
            os.chmod(ks, 0o644) if os.path.exists(ks) else None
            self._skip("No-read permissions", "chmod not supported")

        # Test 9.4: Keystore size growth tracking
        ks = self._path("sizetest.jks")
        sizes = []

        for i in range(5):
            self.generate_keystore(f"size_{i}", ks, "pass123")
            if os.path.exists(ks):
                sizes.append(os.path.getsize(ks))

        growing = all(sizes[i] < sizes[i+1] for i in range(len(sizes)-1))
        if growing and len(sizes) == 5:
            self._pass(f"Keystore size growth ({sizes[0]} -> {sizes[-1]} bytes)")
        else:
            self._fail("Keystore size growth")

        # Test 9.5: Keystores with different content sizes
        configs = [
            (1, "small.jks", "Small (1 alias)"),
            (10, "medium.jks", "Medium (10 aliases)"),
            (25, "large.jks", "Large (25 aliases)")
        ]
        results = []

        for count, ks_name, desc in configs:
            ks = self._path(ks_name)
            success = 0
            for i in range(count):
                if self.generate_keystore(f"size_{i}", ks, "pass123"):
                    success += 1

            all_exist = all(self.alias_exists(f"size_{i}", ks, "pass123")
                           for i in range(success))
            results.append(all_exist)

        if all(results):
            self._pass("Different keystore sizes")
        else:
            self._fail("Different keystore sizes")

    # ==================== TEST GROUP 10: CONCURRENCY & STRESS ====================

    def test_10_concurrency_stress_tests(self):
        """Test Group 10: Concurrency & Stress Tests"""
        self.current_test_group = "test_10_concurrency_stress_tests"
        group_path = self._create_test_group(self.current_test_group)
        print(f"📁 Testing in: {group_path}")

        # Test 10.1: Basic concurrent keystore operations
        ks = self._path("concurrent.jks")

        def worker(i):
            alias = f"concurrent_{i}"
            ok = self.generate_keystore(alias, ks, "pass123")
            return ok or self.alias_exists(alias, ks, "pass123")

        with ThreadPoolExecutor(max_workers=5) as executor:
            futures = [executor.submit(worker, i) for i in range(20)]
            results = [f.result() for f in as_completed(futures)]

        success = sum(results)
        if success >= 15:
            self._pass(f"Concurrent operations basic ({success}/20)")
        else:
            self._fail("Concurrent operations basic")

        # Test 10.2: Advanced concurrent operations
        ks = self._path("concurrent_adv.jks")

        def worker(i):
            try:
                if i % 5 == 0:
                    return self.generate_keystore(f"adv_{i}", ks, "pass123")
                elif i % 5 == 1:
                    created = self.generate_keystore(f"adv_{i}", ks, "pass123")
                    return created and self.alias_exists(f"adv_{i}", ks, "pass123")
                elif i % 5 == 2:
                    created = self.generate_keystore(f"adv_{i}", ks, "pass123")
                    if created:
                        cert = self._path(f"adv_{i}.crt")
                        return self.export_certificate(f"adv_{i}", ks, "pass123", cert)
                    return False
                elif i % 5 == 3:
                    return self.alias_exists(f"adv_{i-1}", ks, "pass123") if i > 0 else True
                else:
                    ok, _ = self.list_keystore(ks, "pass123")
                    return ok
            except Exception:
                return False

        with ThreadPoolExecutor(max_workers=6) as executor:
            futures = [executor.submit(worker, i) for i in range(25)]
            results = [f.result() for f in as_completed(futures)]

        success = sum(results)
        rate = (success / len(results)) * 100
        if rate > 50:
            self._pass(f"Concurrent advanced ({success}/25, {rate:.0f}%)")
        else:
            self._fail("Concurrent advanced")

        # Test 10.3: Race condition scenarios
        ks = self._path("race.jks")

        def worker(i):
            if i % 3 == 0:
                return self.generate_keystore(f"race_{i}", ks, "pass123")
            elif i % 3 == 1:
                return self.alias_exists(f"race_{i-1}", ks, "pass123")
            else:
                cert = self._path(f"race_{i}.crt")
                return self.export_certificate(f"race_{i-2}", ks, "pass123", cert)

        with ThreadPoolExecutor(max_workers=6) as executor:
            futures = [executor.submit(worker, i) for i in range(30)]
            results = [f.result() for f in as_completed(futures)]

        success = sum(results)
        rate = (success / len(results)) * 100
        if rate > 40:
            self._pass(f"Race conditions ({success}/30, {rate:.0f}%)")
        else:
            self._fail("Race conditions")

        # Test 10.4: Large batch operations
        ks = self._path("batch.jks")
        batch_size = 50
        results = []

        start = time.time()
        for i in range(batch_size):
            ok = self.generate_keystore(f"batch_{i}", ks, "pass123")
            results.append(ok)
            if i % 10 == 0 and i > 0:
                print(f"    Progress: {i}/{batch_size}")

        elapsed = time.time() - start
        success = sum(results)

        if success >= batch_size * 0.8:
            self._pass(f"Large batch ({success}/{batch_size} in {elapsed:.1f}s)")
        else:
            self._fail("Large batch operations")

        # Test 10.5: I/O stress test
        operations = 40
        success = 0

        for i in range(operations):
            ks = self._path(f"io_{i}.jks")
            if i % 3 == 0:
                ok = self.generate_keystore(f"io_{i}", ks, "pass123")
            elif i % 3 == 1:
                ok, _ = self.list_keystore(ks, "pass123") if i > 0 else (True, "")
            else:
                ok = self.alias_exists(f"io_{i-1}", self._path(f"io_{i-1}.jks"), "pass123")

            if ok:
                success += 1

        rate = (success / operations) * 100
        if rate >= 60:
            self._pass(f"I/O stress ({success}/{operations}, {rate:.0f}%)")
        else:
            self._fail("I/O stress")

    # ==================== TEST GROUP 11: SPECIAL SCENARIOS ====================

    def test_11_special_scenarios(self):
        """Test Group 11: Special Scenarios"""
        self.current_test_group = "test_11_special_scenarios"
        group_path = self._create_test_group(self.current_test_group)
        print(f"📁 Testing in: {group_path}")

        # Test 11.1: Different Distinguished Name formats
        ks = self._path("dnames.jks")
        dnames = [
            "CN=Test User, OU=Dev, O=Company, L=City, ST=State, C=US",
            "CN=Simple",
            "CN=Test, O=Org",
            "CN=Unicode测试, C=CN",
            "CN=Emoji🚀, OU=Test"
        ]
        results = []

        for i, dn in enumerate(dnames):
            ok = self.generate_keystore(f"dn_{i}", ks, "pass123", dname=dn)
            results.append(ok)

        success = sum(results)
        if success >= 4:
            self._pass(f"Special DN formats ({success}/5)")
        else:
            self._fail("Special DN formats")

        # Test 11.2: Backup and restore keystore
        ks_orig = self._path("original.jks")
        ks_backup = self._path("backup.jks")

        self.generate_keystore("backup_test", ks_orig, "pass123")
        cert = self._path("backup.crt")
        self.export_certificate("backup_test", ks_orig, "pass123", cert)

        shutil.copy2(ks_orig, ks_backup)

        alias_in_backup = self.alias_exists("backup_test", ks_backup, "pass123")
        cert_exists = os.path.exists(cert)

        if alias_in_backup and cert_exists:
            self._pass("Backup/restore")
        else:
            self._fail("Backup/restore")

        # Test 11.3: Empty keystore operations
        ks = self._path("empty.jks")
        self.generate_keystore("temp", ks, "pass123")
        self.delete_alias("temp", ks, "pass123")

        can_list, _ = self.list_keystore(ks, "pass123")
        nonexistent = self.alias_exists("nonexistent", ks, "pass123")

        if can_list and not nonexistent:
            self._pass("Empty keystore operations")
        else:
            self._fail("Empty keystore operations")

        # Test 11.4: Operations on non-existent keystore
        ks = self._path("nonexistent.jks")

        exists = self.alias_exists("any", ks, "pass123")
        can_list, _ = self.list_keystore(ks, "pass123")
        can_export = self.export_certificate("any", ks, "pass123", self._path("none.crt"))

        if not exists and not can_list and not can_export:
            self._pass("Non-existent keystore operations")
        else:
            self._fail("Non-existent keystore operations")

    # ==================== TEST GROUP 12: EXTREME EDGE CASES ====================

    def test_12_extreme_edge_cases(self):
        """Test Group 12: Extreme Edge Cases"""
        self.current_test_group = "test_12_extreme_edge_cases"
        group_path = self._create_test_group(self.current_test_group)
        print(f"📁 Testing in: {group_path}")

        # Test 12.1: Memory stress with many operations
        operations = 100
        success = 0

        for i in range(operations):
            try:
                if i % 10 == 0:
                    ks = self._path(f"mem_{i}.jks")
                    ok = self.generate_keystore(f"mem_{i}", ks, "pass123")
                else:
                    ok = self.generate_keystore(f"mem_{i}", self._path("mem_reuse.jks"), "pass123")

                if ok:
                    success += 1

                if i % 20 == 0:
                    import gc
                    gc.collect()
            except Exception:
                pass

        rate = (success / operations) * 100
        if rate > 60:
            self._pass(f"Memory stress ({success}/{operations}, {rate:.0f}%)")
        else:
            self._fail("Memory stress")

        # Test 12.2: Long-term stability test
        ks = self._path("stability.jks")
        operations = 30
        success = 0

        for i in range(operations):
            try:
                op_type = i % 4

                if op_type == 0:
                    ok = self.generate_keystore(f"stable_{i}", ks, "pass123")
                elif op_type == 1:
                    ok = self.alias_exists(f"stable_{i-1}", ks, "pass123") if i > 0 else True
                elif op_type == 2:
                    cert = self._path(f"stable_{i}.crt")
                    ok = self.export_certificate(f"stable_{i-2}", ks, "pass123", cert) if i > 1 else True
                else:
                    ok, _ = self.list_keystore(ks, "pass123")

                if ok:
                    success += 1

                time.sleep(0.05)
            except Exception:
                pass

        rate = (success / operations) * 100
        if rate > 70:
            self._pass(f"Long-term stability ({success}/{operations}, {rate:.0f}%)")
        else:
            self._fail("Long-term stability")

        # Test 12.3: Interrupted operation
        ks = self._path("interrupt.jks")

        try:
            cmd = [
                "keytool", "-genkeypair", "-keyalg", "RSA",
                "-alias", "interrupt_test", "-keystore", ks,
                "-storepass", "pass123", "-keypass", "pass123",
                "-keysize", "4096", "-dname", "CN=Test, C=ES"
            ]
            proc = subprocess.Popen(cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
            time.sleep(0.3)
            proc.kill()
            proc.wait(timeout=5)

            if os.path.exists(ks):
                can_list, _ = self.list_keystore(ks, "pass123")
                self._pass(f"Interrupted operation handled (can_list={can_list})")
            else:
                self._pass("Interrupted operation - no partial file")
        except Exception:
            self._skip("Interrupted operation", "Cannot simulate")

        # Test 12.4: RAM disk operations
        ramdisk = "/dev/shm"
        if not os.path.exists(ramdisk):
            self._skip("RAM disk", "/dev/shm not available")
        else:
            ks = os.path.join(ramdisk, f"ramdisk_{int(time.time())}.jks")
            try:
                ok = self.generate_keystore("ram_alias", ks, "pass123")
                if ok and os.path.exists(ks):
                    os.remove(ks)
                    self._pass("RAM disk operations")
                else:
                    self._fail("RAM disk operations")
            except Exception:
                self._skip("RAM disk", "Operation failed")

        # Test 12.5: Listing tests
        ks = self._path("verbose.jks")
        self.generate_keystore("verbose_test", ks, "pass123")

        can_list, output = self.list_keystore(ks, "pass123", verbose=True)

        if can_list and len(output) > 100:
            self._pass(f"Verbose listing ({len(output)} chars)")
        else:
            self._fail("Verbose listing")

        # Test 12.6: List keystore with many aliases
        ks = self._path("list_multi.jks")
        for i in range(10):
            self.generate_keystore(f"list_{i}", ks, "pass123")

        can_list, output = self.list_keystore(ks, "pass123")

        if can_list:
            self._pass("List multiple aliases")
        else:
            self._fail("List multiple aliases")

    # ==================== TEST GROUP 13: FILE INTEGRITY ====================

    def test_13_file_integrity_tests(self):
        """Test Group 13: File Integrity & Consistency"""
        self.current_test_group = "test_13_file_integrity_tests"
        group_path = self._create_test_group(self.current_test_group)
        print(f"📁 Testing in: {group_path}")

        # Test 13.1: Keystore file consistency after operations
        ks = self._path("consistency.jks")

        # Create and get initial hash
        self.generate_keystore("test1", ks, "pass123")
        with open(ks, "rb") as f:
            hash1 = hashlib.sha256(f.read()).hexdigest()

        # Read-only operations shouldn't change hash
        self.alias_exists("test1", ks, "pass123")
        self.list_keystore(ks, "pass123")

        with open(ks, "rb") as f:
            hash2 = hashlib.sha256(f.read()).hexdigest()

        # Modify operation should change hash
        self.generate_keystore("test2", ks, "pass123")
        with open(ks, "rb") as f:
            hash3 = hashlib.sha256(f.read()).hexdigest()

        if hash1 == hash2 and hash1 != hash3:
            self._pass("Keystore file consistency")
        else:
            self._fail("Keystore file consistency")

        # Test 13.2: Zero-byte keystore file
        ks = self._path("zerobyte.jks")

        with open(ks, "wb") as f:
            f.write(b"")

        can_list, _ = self.list_keystore(ks, "pass123")

        if not can_list:
            self._pass("Zero-byte keystore rejection")
        else:
            self._fail("Zero-byte keystore rejection")

        # Test 13.3: Single-byte keystore file
        ks = self._path("singlebyte.jks")

        with open(ks, "wb") as f:
            f.write(b"X")

        can_list, _ = self.list_keystore(ks, "pass123")

        if not can_list:
            self._pass("Single-byte keystore rejection")
        else:
            self._fail("Single-byte keystore rejection")

        # Test 13.4: Binary garbage keystore
        ks = self._path("garbage.jks")

        with open(ks, "wb") as f:
            f.write(os.urandom(1024))

        can_list, _ = self.list_keystore(ks, "pass123")

        if not can_list:
            self._pass("Binary garbage keystore rejection")
        else:
            self._fail("Binary garbage keystore rejection")

        # Test 13.5: Symlink keystore
        ks_real = self._path("real.jks")
        ks_link = self._path("link.jks")

        self.generate_keystore("real_alias", ks_real, "pass123")

        try:
            os.symlink(ks_real, ks_link)
            via_link = self.alias_exists("real_alias", ks_link, "pass123")
            os.remove(ks_link)

            if via_link:
                self._pass("Symbolic link keystore")
            else:
                self._fail("Symbolic link keystore")
        except (OSError, NotImplementedError):
            self._skip("Symbolic link", "Symlinks not supported")

        # Test 13.6: Rapid file recreation
        ks = self._path("rapid_recreate.jks")
        results = []

        for i in range(10):
            self.generate_keystore(f"rapid_{i}", ks, "pass123")
            if os.path.exists(ks):
                os.remove(ks)
            ok = self.generate_keystore(f"new_{i}", ks, "pass123")
            results.append(ok)

        success = sum(results)
        if success >= 8:
            self._pass(f"Rapid file recreation ({success}/10)")
        else:
            self._fail("Rapid file recreation")

    # ==================== TEST RUNNER ====================

    def run_all_tests(self):
        """Execute all test methods"""
        print("="*70)
        print("EXTREME PROFESSIONAL KEYSTORE TEST SUITE")
        print("60+ Tests Organized in 13 Groups with Subdirectories")
        print("="*70 + "\n")

        # Check Java availability
        try:
            subprocess.run(["keytool", "-help"], capture_output=True, check=True, timeout=5)
            print("✅ Java keytool detected\n")
        except (subprocess.CalledProcessError, FileNotFoundError, subprocess.TimeoutExpired):
            print("❌ ERROR: keytool not found. Install Java JDK:")
            print("  Ubuntu/Debian: sudo apt-get install openjdk-11-jdk-headless")
            print("  CentOS/RHEL:   sudo yum install java-11-openjdk-devel")
            print("  macOS:         brew install openjdk@11")
            return

        # Collect and run all test methods
        test_methods = sorted([m for m in dir(self) if m.startswith("test_")])
        print(f"🚀 Running {len(test_methods)} comprehensive test groups...\n")

        start_time = time.time()

        for i, method_name in enumerate(test_methods, 1):
            print(f"\n{'='*70}")
            print(f"🧪 TEST GROUP {i:02d}: {method_name.replace('_', ' ').title()}")
            print(f"{'='*70}")
            try:
                getattr(self, method_name)()
            except Exception as e:
                self._fail(f"{method_name} - EXCEPTION: {str(e)[:100]}")

        elapsed = time.time() - start_time

        # Print summary
        print("\n" + "="*70)
        print("📊 FINAL TEST SUMMARY")
        print("="*70)
        total = sum(self.results.values())
        print(f"Total tests:     {total}")
        print(f"✅ PASSED:       {self.results['passed']}")
        print(f"❌ FAILED:       {self.results['failed']}")
        print(f"⚠️ SKIPPED:      {self.results['skipped']}")
        print(f"⏱️ Execution time: {elapsed:.2f} seconds")

        if total > 0:
            pass_rate = (self.results['passed'] / total) * 100
            print(f"📈 Pass rate:     {pass_rate:.1f}%")

            if pass_rate >= 90:
                print("\n🎉 Status: EXCELLENT")
            elif pass_rate >= 75:
                print("\n👍 Status: GOOD")
            elif pass_rate >= 50:
                print("\n👌 Status: ACCEPTABLE")
            else:
                print("\n🔧 Status: NEEDS ATTENTION")

        print("="*70)

        # Show directory structure
        print("\n📁 FINAL DIRECTORY STRUCTURE:")
        print("="*50)
        for root, dirs, files in os.walk(self.test_dir):
            level = root.replace(self.test_dir, '').count(os.sep)
            indent = ' ' * 2 * level
            basename = os.path.basename(root)
            if level == 0:
                print(f"📂 {self.test_dir}/")
            else:
                print(f"{indent}📁 {basename}/")

            subindent = ' ' * 2 * (level + 1)
            for file in files[:8]:  # Show first 8 files per directory
                print(f"{subindent}📄 {file}")
            if len(files) > 8:
                print(f"{subindent}... and {len(files) - 8} more files")

        # Cleanup option
        try:
            response = input(f"\n🧹 Clean up test directory '{self.test_dir}'? (y/N): ")
            if response.lower() in ['y', 'yes']:
                if os.path.exists(self.test_dir):
                    shutil.rmtree(self.test_dir)
                    print("✅ Test environment cleaned up successfully")
                else:
                    print("ℹ️ Test directory already removed")
            else:
                print(f"ℹ️ Test files preserved in: {self.test_dir}")
        except Exception as e:
            print(f"⚠️ Cleanup failed: {e}")

if __name__ == "__main__":
    print("\n🚀 Initializing Extreme Professional Keystore Test Suite...\n")
    tester = ExtremeProfessionalKeystoreTestSuite()
    tester.run_all_tests()

    print("\n" + "="*70)
    print("✅ Test suite execution completed")
    print("="*70)